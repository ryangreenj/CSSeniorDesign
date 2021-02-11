package com.education.education.promptlet;

import com.education.education.promptlet.repositories.PromptletRepository;
import com.education.education.promptlet.repositories.entities.PromptletEntity;
import com.education.education.promptlet.repositories.entities.mappers.PromptletEntityToPromptletMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.education.education.promptlet.helpers.RandomPromptletEntity.getRandomPromptletEntityBuilder;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.testerhelper.GenerateMany.generateListOf;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MongoPromptletDataAccessServiceTest.class})
class MongoPromptletDataAccessServiceTest {


    @MockBean
    private PromptletRepository promptletRepository;

    private MongoPromptletDataAccessService mongoPromptletDataAccessService;

    @BeforeEach
    public void setup(){
        this.mongoPromptletDataAccessService = new MongoPromptletDataAccessService(promptletRepository);
    }


    @Test
    void createPromptlet_shouldCreateAndSaveNewPromptletEntity() {
        final PromptletEntity promptletEntity = getRandomPromptletEntityBuilder().build();

        when(promptletRepository.save(any())).thenReturn(promptletEntity);

        final String actualPromptletEntityId =  mongoPromptletDataAccessService.createPromptlet(
                promptletEntity.getPrompt(),
                PROMPTLET_TYPE.fromString(promptletEntity.getPromptletType()),
                promptletEntity.getResponsePool(),
                promptletEntity.getCorrectAnswer());

        verify(promptletRepository).save(any());
        assertThat(actualPromptletEntityId).isEqualTo(promptletEntity.getId());
    }

    @Test
    void getPromptlets_shouldGetPromptlets_andCallFindsPromptletEntitiesById() {
        final List<String> promptletIds = generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(1,10));
        final List<PromptletEntity> promptletEntities = promptletIds.stream()
                .map(x -> getRandomPromptletEntityBuilder().id(x).build())
                .collect(toList());

        promptletEntities.forEach(x ->
                when(promptletRepository.findPromptletEntityById(x.getId())).thenReturn(x));

        final List<Promptlet> actualPromptlets = mongoPromptletDataAccessService.getPromptlets(promptletIds);

        verify(promptletRepository, times(promptletEntities.size())).findPromptletEntityById(any());
        actualPromptlets.forEach(x ->
                assertThat(x.getId()).isIn(promptletIds));
    }
}
