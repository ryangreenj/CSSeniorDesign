package com.education.education.promptlet.repositories.entities.mappers;

import com.education.education.promptlet.PROMPTLET_TYPE;
import com.education.education.promptlet.Promptlet;
import com.education.education.promptlet.repositories.entities.PromptletEntity;

import static com.education.education.promptlet.Promptlet.aPromptletBuilder;
import static com.education.education.promptlet.repositories.entities.PromptletEntity.aPromptletEntityBuilder;

public class PromptletEntityToPromptletMapper {

    public static Promptlet mapPromptletEntityToPromptlet(final PromptletEntity promptletEntity){
        return aPromptletBuilder()
                .id(promptletEntity.getId())
                .prompt(promptletEntity.getPrompt())
                .PROMPTLET_TYPE(PROMPTLET_TYPE.fromString(promptletEntity.getPromptletType()))
                .responsePool(promptletEntity.getResponsePool())
                .correctAnswer(promptletEntity.getCorrectAnswer())
                .userResponses(promptletEntity.getUserResponses())
                .build();
    }
}
