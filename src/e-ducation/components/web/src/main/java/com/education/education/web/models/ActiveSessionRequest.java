package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ActiveSessionRequest {

    private final String courseId;
    private final String sessionId;

    public ActiveSessionRequest(
            @JsonProperty("courseId") final String courseId,
            @JsonProperty("sessionId") final String sessionId
            ) {
        this.courseId = courseId;
        this.sessionId = sessionId;
    }
}
