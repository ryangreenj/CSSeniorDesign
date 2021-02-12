package com.education.education.web.helpers;


import com.education.education.user.User;
import com.education.education.web.models.UserRequest;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.user.User.aUserBuilder;

public class RandomUser {

    public static User randomUser(){
        return aUserBuilder()
                .username(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .password(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .build();
    }

    public static UserRequest randomUserRequest(){
        return new UserRequest(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)));
    }
}
