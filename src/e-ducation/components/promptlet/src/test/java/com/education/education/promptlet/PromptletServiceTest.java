package com.education.education.promptlet;

import com.education.education.testerhelper.Chance;
import com.education.education.testerhelper.GenerateMany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.education.education.promptlet.helpers.RandomPromptlet.getRandomPromptletBuilder;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomBoolean;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.testerhelper.GenerateMany.generateListOf;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PromptletService.class})
class PromptletServiceTest {

    @MockBean
    @Qualifier("MongoPromptletDataAccessService")
    private PromptletDataAccessService promptletDataAccessService;

    private PromptletService promptletService;

    @BeforeEach
    public void setup(){
        this.promptletService = new PromptletService(promptletDataAccessService);
    }

    @Test
    void createPromptlet_shouldCallCreatePromptlet_andReturnPromptletId(){
        final String promptId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String prompt = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final PROMPTLET_TYPE promptlet_type = Chance.getRandomEnum(PROMPTLET_TYPE.class);
        final List<String> answerPool = generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(1,10));
        final List<String> correctAnswer = answerPool.stream()
                .filter(x -> getRandomBoolean())
                .collect(toList());

        when(promptletDataAccessService.createPromptlet(prompt, promptlet_type, answerPool, correctAnswer)).thenReturn(promptId);
        final String actualPromptletId = promptletService.createPromptlet(prompt, promptlet_type, answerPool, correctAnswer);


        verify(promptletDataAccessService).createPromptlet(prompt, promptlet_type, answerPool, correctAnswer);
        assertThat(actualPromptletId).isIn(promptId);
    }

    @Test
    void getPromptlets_shouldCallGetPromptlets_andReturnPromptlets(){
        final List<String> promptletIds = generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(1,10));
        final List<Promptlet> promptlets = promptletIds.stream()
                .map(x -> getRandomPromptletBuilder().id(x).build())
                .collect(toList());

        when(promptletDataAccessService.getPromptlets(promptletIds)).thenReturn(promptlets);

        final List<Promptlet> actualPromptlets = promptletService.getPromptlets(promptletIds);
        verify(promptletDataAccessService).getPromptlets(promptletIds);
        actualPromptlets.forEach(x ->
                assertThat(x.getId()).isIn(promptletIds));
    }
}
