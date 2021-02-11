package com.education.education.promptlet.helpers;

import com.education.education.promptlet.PROMPTLET_TYPE;
import com.education.education.promptlet.Promptlet;
import com.education.education.promptlet.repositories.entities.PromptletEntity;
import com.education.education.testerhelper.GenerateMany;

import java.util.ArrayList;
import java.util.List;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomBoolean;
import static com.education.education.testerhelper.Chance.getRandomEnum;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static java.util.stream.Collectors.toList;


public class RandomPromptletEntity {

    static public PromptletEntity.PromptletEntityBuilder getRandomPromptletEntityBuilder(){
        List<String> answerPool = GenerateMany.generateListOf(() ->
                        getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(1,10));

        return PromptletEntity.aPromptletEntityBuilder()
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .prompt(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .promptletType(getRandomEnum(PROMPTLET_TYPE.class).getText())
                .responsePool(answerPool)
                .correctAnswer(answerPool.stream().filter(x -> getRandomBoolean()).collect(toList()))
                .userResponses(new ArrayList<>());
    }
}
