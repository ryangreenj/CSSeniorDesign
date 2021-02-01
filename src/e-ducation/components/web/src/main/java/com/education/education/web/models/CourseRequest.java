package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CourseRequest {

    private final List<String> courseIds;

    public CourseRequest(
            @JsonProperty("ids") final List<String> courseIds) {
        this.courseIds = courseIds;
    }
}
