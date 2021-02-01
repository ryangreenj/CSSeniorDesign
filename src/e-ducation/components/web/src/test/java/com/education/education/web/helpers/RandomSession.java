package com.education.education.web.helpers;

import com.education.education.web.models.CourseCreationRequest;
import com.education.education.web.models.SessionRequest;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;

public class RandomSession {

    public static SessionRequest randomSessionRequest(){
        return new SessionRequest(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)));
    }
}
