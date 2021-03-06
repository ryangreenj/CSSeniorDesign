package com.education.education.promptlet;

import com.education.education.promptlet.repositories.PromptletRepository;
import com.education.education.promptlet.repositories.UserResponseRepository;
import com.education.education.promptlet.repositories.entities.PromptletEntity;
import com.education.education.promptlet.repositories.entities.UserResponseEntity;
import com.education.education.promptlet.repositories.entities.mappers.UserResponseEntityToUserResponseMapper;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.education.education.promptlet.repositories.entities.PromptletEntity.aPromptletEntityBuilder;
import static com.education.education.promptlet.repositories.entities.UserResponseEntity.aUserResponseEntityBuilder;
import static com.education.education.promptlet.repositories.entities.mappers.PromptletEntityToPromptletMapper.mapPromptletEntityToPromptlet;
import static com.education.education.promptlet.repositories.entities.mappers.UserResponseEntityToUserResponseMapper.mapUserResponseEntityToUserResponse;
import static java.util.stream.Collectors.toList;

@Repository("MongoPromptletDataAccessService")
public class MongoPromptletDataAccessService implements PromptletDataAccessService{

    private final PromptletRepository promptletRepository;

    private final UserResponseRepository userResponseRepository;

    @Autowired
    public MongoPromptletDataAccessService(final PromptletRepository promptletRepository,
                                           final UserResponseRepository userResponseRepository) {
        this.promptletRepository = promptletRepository;
        this.userResponseRepository = userResponseRepository;
    }

    @Override
    public String createPromptlet(final String prompt, final PROMPTLET_TYPE promptletType,
                                  final List<String> responsePool, final List<String> correctAnswer) {
        try{
            return promptletRepository.save(
                    aPromptletEntityBuilder()
                            .prompt(prompt)
                            .promptletType(promptletType.toString())
                            .responsePool(responsePool)
                            .correctAnswer(correctAnswer)
                            .userResponses(new ArrayList<>())
                            .isVisible(false)
                            .build()).getId();
        } catch (MongoException mongoException){
            throw new RuntimeException("Failed to save promptlet to mongo. | " + mongoException.getMessage());
        }
    }

    @Override
    public List<Promptlet> getPromptlets(final List<String> promptletIds) {
        try{
            return promptletIds.stream()
                    .map(x -> mapPromptletEntityToPromptlet((promptletRepository.findPromptletEntityById(x))))
                    .collect(toList());
        } catch (MongoException mongoException){
            throw new RuntimeException("Failed to save promptlet to mongo. | " + mongoException.getMessage());
        }
    }

    @Override
    public UserResponse answerPromptlet(final String promptletId, final String profileId, final List<String> response) {
        final Instant instant = Instant.now();
        final UserResponseEntity userResponseEntity = userResponseRepository.save(aUserResponseEntityBuilder()
                .profileId(profileId)
                .response(response)
                .timestamp(instant.getEpochSecond())
                .build());

        PromptletEntity promptletEntity = promptletRepository.findPromptletEntityById(promptletId);
        promptletEntity.setUserResponses(promptletEntity.getUserResponses().stream().filter(x -> !userResponseRepository.findUserResponseEntityById(x).getProfileId().equals(profileId)).collect(toList()));
        promptletEntity.getUserResponses().add(userResponseEntity.getId());
        promptletRepository.save(promptletEntity);

        return mapUserResponseEntityToUserResponse(userResponseEntity);
    }

    @Override
    public List<UserResponse> getPromptletResponse(List<String> responseIds) {
        try{
            return responseIds.stream()
                    .map(x -> mapUserResponseEntityToUserResponse((userResponseRepository.findUserResponseEntityById(x))))
                    .collect(toList());
        } catch (MongoException mongoException){
            throw new RuntimeException("Failed to save promptlet to mongo. | " + mongoException.getMessage());
        }
    }

    @Override
    public void activatePromptlet(final String promptletId, final boolean status) {
        final PromptletEntity promptletEntity = promptletRepository.findPromptletEntityById(promptletId);

        promptletEntity.setVisible(status);
        promptletRepository.save(promptletEntity);
    }
}
