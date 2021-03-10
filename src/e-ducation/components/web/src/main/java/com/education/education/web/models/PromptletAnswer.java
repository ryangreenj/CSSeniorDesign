package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class PromptletAnswer {

    final String activeSessionId;
    final String promptletId;
    final String profileId;
    final List<String> response;

    public PromptletAnswer(
            @JsonProperty("activeSessionId") String activeSessionId,
            @JsonProperty("promptletId") String promptletId,
            @JsonProperty("profileId") String profileId,
            @JsonProperty("response") List<String> response) {
        this.activeSessionId = activeSessionId;
        this.promptletId = promptletId;
        this.profileId = profileId;
        this.response = response;
    }
}
