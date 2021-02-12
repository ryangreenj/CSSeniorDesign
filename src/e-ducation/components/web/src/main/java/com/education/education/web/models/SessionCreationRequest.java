package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SessionCreationRequest {

    private final String courseId;
    private final String sessionName;

    public SessionCreationRequest(
            @JsonProperty("courseId") final String courseId,
            @JsonProperty("sessionName") final String sessionName
            ) {
        this.courseId = courseId;
        this.sessionName = sessionName;
    }
}
