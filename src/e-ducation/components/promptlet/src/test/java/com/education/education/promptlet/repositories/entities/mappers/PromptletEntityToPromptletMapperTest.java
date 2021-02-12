package com.education.education.promptlet.repositories.entities.mappers;

import com.education.education.promptlet.PROMPTLET_TYPE;
import com.education.education.promptlet.Promptlet;
import com.education.education.promptlet.repositories.entities.PromptletEntity;
import org.junit.jupiter.api.Test;

import static com.education.education.promptlet.Promptlet.aPromptletBuilder;
import static com.education.education.promptlet.helpers.RandomPromptletEntity.getRandomPromptletEntityBuilder;
import static com.education.education.promptlet.repositories.entities.mappers.PromptletEntityToPromptletMapper.mapPromptletEntityToPromptlet;
import static org.assertj.core.api.Assertions.assertThat;

class PromptletEntityToPromptletMapperTest {

    @Test
    void mapPromptletEntityToPromptlet_shouldMapPromptletEntityToPromptlet() {
        final PromptletEntity promptletEntity = getRandomPromptletEntityBuilder().build();
        final Promptlet promptlet = aPromptletBuilder()
                .id(promptletEntity.getId())
                .prompt(promptletEntity.getPrompt())
                .PROMPTLET_TYPE(PROMPTLET_TYPE.fromString(promptletEntity.getPromptletType()))
                .responsePool(promptletEntity.getResponsePool())
                .correctAnswer(promptletEntity.getCorrectAnswer())
                .userResponses(promptletEntity.getUserResponses())
                .build();

        assertThat(mapPromptletEntityToPromptlet(promptletEntity))
                .isEqualToComparingFieldByField(promptlet);
    }
}
