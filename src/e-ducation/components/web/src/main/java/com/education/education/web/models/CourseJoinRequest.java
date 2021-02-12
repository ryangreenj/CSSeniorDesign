package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CourseJoinRequest {

    private final String profileId;
    private final String courseId;

    public CourseJoinRequest(
            @JsonProperty("profileId") String profileId,
            @JsonProperty("courseId") String courseId) {
        this.profileId = profileId;
        this.courseId = courseId;
    }
}
