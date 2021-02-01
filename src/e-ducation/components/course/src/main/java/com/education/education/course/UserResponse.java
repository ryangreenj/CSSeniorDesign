package com.education.education.course;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {

    private final String id;
    private final String response;
    private final Profile profileId;
}
