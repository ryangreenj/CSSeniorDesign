package com.education.education.course;

import com.education.education.session.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {CourseService.class})
class CourseServiceTest {

    @MockBean
    @Qualifier("MongoCourseDataAccessService")
    private CourseDataAccessService courseDataAccessService;

    @MockBean
    private SessionService sessionService;

    private CourseService courseService;

    @BeforeEach
    public void setup(){
        this.courseService = new CourseService(courseDataAccessService, sessionService);
    }

    @Test
    void createCourse_shouldCallInsertCourse() {
        final String courseName = getRandomAlphaNumericString(getRandomNumberBetween(5,20));

        doNothing().when(courseDataAccessService).insertCourse(courseName);

        courseService.createCourse(courseName);
        verify(courseDataAccessService).insertCourse(courseName);
    }
}
