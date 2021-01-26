package com.education.education.web.models;

import com.education.education.user.User;
import lombok.Builder;

//@Builder
public class AuthenticationResponse {

    private final String jwt;
    private final User userResponse;

    public AuthenticationResponse(String jwt, User userResponse) {
        this.jwt = jwt;
        this.userResponse = userResponse;
    }

    public String getJwt() {
        return jwt;
    }

    public User getUserResponse(){
        return userResponse;
    }
}
