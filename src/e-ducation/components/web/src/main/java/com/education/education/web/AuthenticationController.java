package com.education.education.web;

import com.education.education.authentication.JwtUtil;
import com.education.education.user.UserService;
import com.education.education.web.models.AuthenticationRequest;
import com.education.education.web.models.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.education.education.authentication.exceptions.UserException.InvalidUserCredentials;


@RestController
@RequestMapping("")
@EnableWebMvc
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtUtil jwtTokenUtil;

    @Autowired
    public AuthenticationController(final AuthenticationManager authenticationManager, final UserService userDetailsService, final JwtUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse createAuthenticationToken(@RequestBody final AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw InvalidUserCredentials(authenticationRequest.getUsername());
        }

        final String jwt = jwtTokenUtil.generateToken(userService.loadUserByUsername(authenticationRequest.getUsername()));
        return new AuthenticationResponse(jwt, userService.getUser(authenticationRequest.getUsername()));
    }

}
