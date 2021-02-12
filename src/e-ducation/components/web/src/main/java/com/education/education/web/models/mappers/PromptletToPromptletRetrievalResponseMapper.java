package com.education.education.web.models.mappers;

import com.education.education.promptlet.Promptlet;
import com.education.education.web.models.PromptletRetrievalResponse;

public class PromptletToPromptletRetrievalResponseMapper {

    public static PromptletRetrievalResponse mapPromptletToPromptletRetrievalResponse(final Promptlet promptlet){
        return PromptletRetrievalResponse.builder()
                .id(promptlet.getId())
                .prompt(promptlet.getPrompt())
                .PROMPTLET_TYPE(promptlet.getPROMPTLET_TYPE().toString())
                .responsePool(promptlet.getResponsePool())
                .correctAnswer(promptlet.getCorrectAnswer())
                .userResponses(promptlet.getUserResponses())
                .build();
    }
}
