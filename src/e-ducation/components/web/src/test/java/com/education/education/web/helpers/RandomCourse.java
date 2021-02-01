package com.education.education.web.helpers;

import com.education.education.web.models.CourseRequest;
import com.education.education.web.models.UserRequest;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;

public class RandomCourse {

    public static CourseRequest randomCourseRequest(){
        return new CourseRequest(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)));
    }
}
