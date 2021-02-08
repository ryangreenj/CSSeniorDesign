package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PromptletRetrievalResponse {

    private final String id;
    private final String prompt;
    private final String PROMPTLET_TYPE;
    private final List<String> responsePool;
    private final List<String> correctAnswer;
    private final List<String> userResponses;

}
