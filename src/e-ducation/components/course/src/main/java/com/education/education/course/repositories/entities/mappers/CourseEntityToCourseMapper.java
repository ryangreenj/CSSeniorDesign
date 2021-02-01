package com.education.education.course.repositories.entities.mappers;

import com.education.education.course.Course;
import com.education.education.course.repositories.entities.CourseEntity;
import com.education.education.session.Session;

import java.util.List;

import static com.education.education.course.Course.aCourseBuilder;

public class CourseEntityToCourseMapper {

    public static Course mapCourseEntityToCourse(final CourseEntity courseEntity){
        return aCourseBuilder()
                .className(courseEntity.getClassName())
                .id(courseEntity.getId())
                .sessionIds(courseEntity.getSessionIds())
                .build();
    }
}
