package com.education.education.course.helpers;

import com.education.education.course.repositories.entities.CourseEntity;
import com.education.education.testerhelper.GenerateMany;

import java.util.ArrayList;

import static com.education.education.course.repositories.entities.CourseEntity.aCourseEntityBuilder;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;

public class RandomCourseEntity {

    public static CourseEntity.CourseEntityBuilder randomCourseEntityBuilder(){
        return aCourseEntityBuilder()
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .className(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .sessionIds(GenerateMany.generateListOf(
                        () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                        getRandomNumberBetween(0,20)));
    }

    public static CourseEntity randomCourseEntity_new(){
        return aCourseEntityBuilder()
                .className(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .sessionIds(new ArrayList<>())
                .build();
    }
}
