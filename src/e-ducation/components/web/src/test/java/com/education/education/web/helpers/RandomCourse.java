package com.education.education.web.helpers;

import com.education.education.web.models.CourseCreationRequest;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;

public class RandomCourse {

    public static CourseCreationRequest randomCourseRequest(){
        return new CourseCreationRequest(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)));
    }
}
