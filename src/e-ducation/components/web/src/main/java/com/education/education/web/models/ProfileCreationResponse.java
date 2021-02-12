package com.education.education.web.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileCreationResponse {

    private final String id;

    public static ProfileCreationResponseBuilder aProfileCreationResponseBuilder(){
        return ProfileCreationResponse.builder();
    }
}
