package com.education.education.web.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserResponseResponse {

    private final String id;
    private final String profileId;
    private final String profileName;
    private final List<String> response;

    public static UserResponseResponseBuilder aUserResponseResponseBuilder(){
        return UserResponseResponse.builder();
    }
}
