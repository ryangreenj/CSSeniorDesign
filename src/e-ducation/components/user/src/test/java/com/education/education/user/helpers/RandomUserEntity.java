package com.education.education.user.helpers;

import com.education.education.user.repositories.entities.UserEntity;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.user.repositories.entities.UserEntity.aUserEntityBuilder;

public class RandomUserEntity {

    public static UserEntity randomUserEntity(){
        return aUserEntityBuilder()
                .id(getRandomAlphaNumericString((getRandomNumberBetween(5,20))))
                .username(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .password(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .build();
    }

    public static UserEntity randomUserEntity_noId(){
        return aUserEntityBuilder()
                .username(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .password(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .build();
    }
}
