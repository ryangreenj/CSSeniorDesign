package com.education.education.web.helpers;

import com.education.education.session.Session;
import com.education.education.testerhelper.GenerateMany;
import com.education.education.web.models.SessionCreationRequest;
import com.education.education.web.models.SessionRetrievalRequest;

import static com.education.education.session.Session.aSessionBuilder;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.testerhelper.GenerateMany.generateListOf;

public class RandomSession {

    public static SessionCreationRequest randomSessionCreationRequest(){
        return new SessionCreationRequest(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)));
    }

    public static SessionRetrievalRequest randomSessionRetrievalRequest(){
        return new SessionRetrievalRequest(
                generateListOf(
                    () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                    getRandomNumberBetween(0,15)));
    }

    public static Session.SessionBuilder randomSessionBuilder(){
        return aSessionBuilder()
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .sessionName(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .promptletIds(generateListOf(
                        () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                        getRandomNumberBetween(0,10)
                ));
    }
}
