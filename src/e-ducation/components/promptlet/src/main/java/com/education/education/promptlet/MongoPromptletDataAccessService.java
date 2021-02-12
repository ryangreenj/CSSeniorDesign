package com.education.education.promptlet;

import com.education.education.promptlet.repositories.PromptletRepository;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.education.education.promptlet.repositories.entities.PromptletEntity.aPromptletEntityBuilder;
import static com.education.education.promptlet.repositories.entities.mappers.PromptletEntityToPromptletMapper.mapPromptletEntityToPromptlet;
import static java.util.stream.Collectors.toList;

@Repository("MongoPromptletDataAccessService")
public class MongoPromptletDataAccessService implements PromptletDataAccessService{

    private final PromptletRepository promptletRepository;

    @Autowired
    public MongoPromptletDataAccessService(final PromptletRepository promptletRepository) {
        this.promptletRepository = promptletRepository;
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
}
