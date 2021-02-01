package com.education.education.web.models.mappers;

import com.education.education.course.Course;
import com.education.education.web.helpers.RandomCourse;
import com.education.education.web.models.CourseResponse;
import org.junit.jupiter.api.Test;

import static com.education.education.web.helpers.RandomCourse.randomCourseBuilder;
import static com.education.education.web.models.CourseResponse.aCourseResponseBuilder;
import static com.education.education.web.models.mappers.CourseToCourseResponseMapper.mapCourseToCourseResponse;
import static com.education.education.web.models.mappers.UserToUserResponseMapper.mapUserToUserResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CourseToCourseResponseMapperTest {

    @Test
    void mapCourseToCourseResponse_shouldMapUserToUserResponse() {
        final Course course = randomCourseBuilder().build();
        final CourseResponse expectedCourseResponse = aCourseResponseBuilder()
                .id(course.getId())
                .className(course.getClassName())
                .sessionIds(course.getSessionIds())
                .build();

        assertThat(mapCourseToCourseResponse(course)).isEqualToComparingFieldByField(expectedCourseResponse);
    }
}
