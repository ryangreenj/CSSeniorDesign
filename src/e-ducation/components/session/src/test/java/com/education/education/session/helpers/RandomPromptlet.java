package com.education.education.session.helpers;

import com.education.education.promptlet.PROMPTLET_TYPE;
import com.education.education.promptlet.Promptlet;
import com.education.education.testerhelper.Chance;
import com.education.education.testerhelper.GenerateMany;

import java.util.List;
import java.util.stream.Collectors;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomBoolean;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static java.util.stream.Collectors.toList;

public class RandomPromptlet {

    static public Promptlet.PromptletBuilder getRandomPromptletBuilder(){
        List<String> answerPool = GenerateMany.generateListOf(() ->
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(1,10));
        return Promptlet.aPromptletBuilder()
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .prompt(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .PROMPTLET_TYPE(PROMPTLET_TYPE.MULTI_RESPONSE)
                .responsePool(answerPool)
                .correctAnswer(answerPool.stream().filter(x -> getRandomBoolean()).collect(toList()));
    }
}
