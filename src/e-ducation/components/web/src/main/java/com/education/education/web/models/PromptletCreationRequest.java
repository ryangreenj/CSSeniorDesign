package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PromptletCreationRequest {

    final String sessionId;
    final String prompt;
    final String promptlet_type;
    final List<String> answerPool;
    final List<String> correctAnswer;

    public PromptletCreationRequest(
            @JsonProperty("sessionId") String sessionId,
            @JsonProperty("prompt") String prompt,
            @JsonProperty("promptlet_type") String promptlet_type,
            @JsonProperty("answerPool") List<String> answerPool,
            @JsonProperty("correctAnswer") List<String> correctAnswer) {
        this.sessionId = sessionId;
        this.prompt = prompt;
        this.promptlet_type = promptlet_type;
        this.answerPool = answerPool;
        this.correctAnswer = correctAnswer;
    }
}
