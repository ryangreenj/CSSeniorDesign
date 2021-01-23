package com.education.education.web.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {

    private final String id;
    private final String username;

    public static UserResponseBuilder aUserResponseBuilder() {
        return UserResponse.builder();
    }
}
