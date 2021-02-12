package com.education.education.web.helpers;

import com.education.education.course.Course;
import com.education.education.testerhelper.GenerateMany;
import com.education.education.web.models.CourseCreationRequest;
import com.education.education.web.models.CourseRequest;

import static com.education.education.course.Course.aCourseBuilder;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.testerhelper.GenerateMany.generateListOf;

public class RandomCourse {

    public static CourseRequest randomCourseRequest(){
        return new CourseRequest(generateListOf(
                    () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                    getRandomNumberBetween(1,3)));
    }


    public static CourseCreationRequest randomCourseCreationRequest(){
        return new CourseCreationRequest(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)));
    }

    public static Course.CourseBuilder randomCourseBuilder(){
        return aCourseBuilder()
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .className(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .sessionIds(GenerateMany.generateListOf(
                        () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                        getRandomNumberBetween(0,2)));
    }


}
