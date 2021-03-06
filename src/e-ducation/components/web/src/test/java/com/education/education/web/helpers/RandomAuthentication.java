package com.education.education.web.helpers;

import com.education.education.web.models.AuthenticationRequest;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;

public class RandomAuthentication {
    public static AuthenticationRequest randomAuthenticationRequest(){
        return new AuthenticationRequest(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,20))
        );
    }
}
