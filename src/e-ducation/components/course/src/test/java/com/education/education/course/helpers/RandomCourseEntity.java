package com.education.education.course.helpers;

import com.education.education.course.repositories.entities.CourseEntity;

import java.util.ArrayList;

import static com.education.education.course.repositories.entities.CourseEntity.aCourseEntityBuilder;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;

public class RandomCourseEntity {

    public static CourseEntity randomCourseEntity(){
        return aCourseEntityBuilder()
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .className(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
//                .sessionIds(list)
                .build();
    }

    public static CourseEntity randomCourseEntity_new(){
        return aCourseEntityBuilder()
                .className(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .sessionIds(new ArrayList<>())
                .build();
    }
}
