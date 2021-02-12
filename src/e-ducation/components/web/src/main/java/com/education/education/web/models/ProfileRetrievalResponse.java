package com.education.education.web.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProfileRetrievalResponse {

    private final String id;
    private final String username;
    private final List<String> coursesEnrolled;
    private final List<String> coursesOwned;

    public static ProfileRetrievalResponseBuilder aProfileRetrievalResponseBuilder(){
        return ProfileRetrievalResponse.builder();
    }
}
