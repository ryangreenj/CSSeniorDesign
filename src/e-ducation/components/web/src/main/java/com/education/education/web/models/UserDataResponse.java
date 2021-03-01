package com.education.education.web.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDataResponse {

    private final String id;
    private final String username;

    public static UserDataResponseBuilder aUserResponseBuilder() {
        return UserDataResponse.builder();
    }
}
