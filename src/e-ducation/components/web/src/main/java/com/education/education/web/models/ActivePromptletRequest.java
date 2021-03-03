package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ActivePromptletRequest {

    private final String sessionId;
    private final String promptletId;
    private final boolean status;

    public ActivePromptletRequest(
            @JsonProperty("sessionId") final String sessionId,
            @JsonProperty("promptletId") final String promptletId,
            @JsonProperty("status") final boolean status
            ) {
        this.sessionId = sessionId;
        this.promptletId = promptletId;
        this.status = status;
    }
}
