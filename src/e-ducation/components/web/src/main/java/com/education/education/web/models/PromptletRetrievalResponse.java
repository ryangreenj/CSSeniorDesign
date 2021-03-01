package com.education.education.web.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PromptletRetrievalResponse {

    private final String id;
    private final String prompt;
    private final String promptlet_type;
    private final List<String> answerPool;
    private final List<String> correctAnswer;
    private final List<String> userResponses;

}
