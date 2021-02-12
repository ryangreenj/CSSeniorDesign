package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserRequest {

    private final String username;
    private final String password;
    private final String profileId;

    public UserRequest(
            @JsonProperty("username") final String username,
            @JsonProperty("password") final String password,
            @JsonProperty("profileId") final String profileId){
        this.username = username;
        this.password = password;
        this.profileId = profileId;
    }

}
