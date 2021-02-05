package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class SessionRetrievalRequest {

    private final List<String> sessionIds;

    public SessionRetrievalRequest(
            @JsonProperty("ids") final List<String> sessionIds
            ) {
        this.sessionIds = sessionIds;
    }
}
