package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CourseCreationRequest {

    private final String courseName;

    public CourseCreationRequest(
            @JsonProperty("courseName") final String courseName) {
        this.courseName = courseName;
    }
}
