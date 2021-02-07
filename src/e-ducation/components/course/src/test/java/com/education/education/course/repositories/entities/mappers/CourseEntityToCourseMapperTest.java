package com.education.education.course.repositories.entities.mappers;

import com.education.education.course.Course;
import com.education.education.course.helpers.RandomCourseEntity;
import com.education.education.course.repositories.entities.CourseEntity;
import org.junit.jupiter.api.Test;

import static com.education.education.course.Course.aCourseBuilder;
import static com.education.education.course.helpers.RandomCourseEntity.randomCourseEntityBuilder;
import static com.education.education.course.repositories.entities.mappers.CourseEntityToCourseMapper.mapCourseEntityToCourse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CourseEntityToCourseMapperTest {

    @Test
    void mapCourseEntityToCourse_shouldReturnCourseFromCourseEntity() {
        final CourseEntity courseEntity = randomCourseEntityBuilder().build();
        final Course course = aCourseBuilder()
                .id(courseEntity.getId())
                .className(courseEntity.getClassName())
                .sessionIds(courseEntity.getSessionIds())
                .build();

        assertThat(mapCourseEntityToCourse(courseEntity)).isEqualToComparingFieldByField(course);
    }
}
