package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {
    private final String username;
    private final String password;

    public UserRequest(
            @JsonProperty("username") final String username,
            @JsonProperty("password") final String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){return username;}

    public String getPassword(){return password;}
}
