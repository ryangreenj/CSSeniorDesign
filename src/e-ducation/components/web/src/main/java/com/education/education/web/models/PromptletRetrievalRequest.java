package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class PromptletRetrievalRequest {

    private final List<String> promptletIds;

    public PromptletRetrievalRequest(
            @JsonProperty("ids") final List<String> promptletIds
    ) {
        this.promptletIds = promptletIds;
    }
}
