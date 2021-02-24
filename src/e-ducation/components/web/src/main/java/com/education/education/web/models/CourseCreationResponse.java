package com.education.education.web.models;

import lombok.Getter;

@Getter
public class CourseCreationResponse {

    private final String id;

    public CourseCreationResponse(final String id) {
        this.id = id;
    }
}
