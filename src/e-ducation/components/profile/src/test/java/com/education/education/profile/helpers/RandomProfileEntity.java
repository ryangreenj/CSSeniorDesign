package com.education.education.profile.helpers;

import com.education.education.profile.repositories.entities.ProfileEntity;
import com.education.education.testerhelper.Chance;
import com.education.education.testerhelper.GenerateMany;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;

public class RandomProfileEntity {

    public static ProfileEntity.ProfileEntityBuilder getRandomProfileEntityBuilder(){
        return ProfileEntity.aProfileEntityBuilder()
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .username(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .coursesEnrolled(GenerateMany.generateListOf(
                        () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                        getRandomNumberBetween(0,10)))
                .coursesOwned(GenerateMany.generateListOf(
                        () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                        getRandomNumberBetween(0,10)
                ));
    }
}
