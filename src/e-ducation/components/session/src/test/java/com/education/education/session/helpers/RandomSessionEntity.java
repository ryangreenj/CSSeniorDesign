package com.education.education.session.helpers;

import com.education.education.session.repositories.entities.SessionEntity;
import com.education.education.testerhelper.GenerateMany;

import static com.education.education.session.repositories.entities.SessionEntity.aSessionEntityBuilder;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;

public class RandomSessionEntity {

    public static SessionEntity.SessionEntityBuilder getRandomSessionEntity(){
        return aSessionEntityBuilder()
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .sessionName(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .promptlets(GenerateMany.generateListOf(() ->
                        getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                        getRandomNumberBetween(0,10)));
    }
}
