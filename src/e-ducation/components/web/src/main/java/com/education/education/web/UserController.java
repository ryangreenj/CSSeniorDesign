package com.education.education.web;

import com.education.education.user.UserService;
import com.education.education.web.models.UserRequest;
import com.education.education.web.models.UserResponse;
import com.education.education.web.models.mappers.UserToUserResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@EnableWebMvc // TODO: What does this do
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService){
        this.userService = userService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody final UserRequest userRequest){
        return userService.createUser(userRequest.getUsername(), userRequest.getPassword(), userRequest.getProfileId());
    }

    /**   PATH: /user/all   **/
    /**   Get get list of users **/
    // TODO - This should probs be deleted or heavily restricted
    @GetMapping("/all")
    public List<UserResponse> getUsers() {
        return userService.getAllUsers().stream()
                .map(UserToUserResponseMapper::mapUserToUserResponse)
                .collect(toList());
    }
}
