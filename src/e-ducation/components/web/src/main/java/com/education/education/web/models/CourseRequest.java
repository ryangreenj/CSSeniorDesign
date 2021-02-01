package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CourseRequest {

    private final String courseName;

    public CourseRequest(
            @JsonProperty("courseName") final String courseName) {
        this.courseName = courseName;
    }
}
