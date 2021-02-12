package com.education.education.course.helpers;

import com.education.education.session.Session;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.testerhelper.GenerateMany.generateListOf;

public class RandomSession {

    public static Session.SessionBuilder getRandomSessionBuilder() {
        return Session.aSessionBuilder()
                .sessionName(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .promptletIds(generateListOf(
                        () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                        getRandomNumberBetween(0,10)
                ));

    }
}
