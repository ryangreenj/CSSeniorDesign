package com.education.education.web.models.mappers;

import com.education.education.course.Course;
import com.education.education.web.models.CourseResponse;

import static com.education.education.web.models.CourseResponse.aCourseResponseBuilder;

public class CourseToCourseResponseMapper {

    public static CourseResponse mapCourseToCourseResponse(final Course course){
        return aCourseResponseBuilder()
                .className(course.getClassName())
                .id(course.getId())
                .sessionIds(course.getSessionIds())
                .activeSessionId(course.getActiveSessionId())
                .build();
    }
}
