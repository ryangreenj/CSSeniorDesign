package com.education.education.course;

import com.education.education.course.helpers.RandomCourseEntity;
import com.education.education.course.helpers.RandomSession;
import com.education.education.course.repositories.entities.CourseEntity;
import com.education.education.course.repositories.entities.mappers.CourseEntityToCourseMapper;
import com.education.education.session.Session;
import com.education.education.session.SessionService;
import com.education.education.testerhelper.GenerateMany;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.education.education.course.helpers.RandomCourseEntity.randomCourseEntityBuilder;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void getCourses_shouldCallGetCourses_andReturnCoursesById() {
        final List<String> courseIds = GenerateMany.generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(0,10));
        final List<CourseEntity> courseEntities = courseIds.stream()
                .map(x -> randomCourseEntityBuilder().id(x).build())
                .collect(toList());
        final List<Course> expectedCourses = courseEntities.stream()
                .map(CourseEntityToCourseMapper::mapCourseEntityToCourse)
                .collect(toList());

        when(courseDataAccessService.getCourses(courseIds)).thenReturn(courseEntities);

        final List<Course> actualCourses = courseService.getCourses(courseIds);
        verify(courseDataAccessService).getCourses(courseIds);

        final List<String> expectedIds = expectedCourses
                .stream().map(Course::getId).collect(toList());
        actualCourses.forEach(
                x -> Assertions.assertThat(expectedIds).contains(x.getId()));
    }

    @Test
    void getAllCourses_shouldCallGetAllCourses_andReturnAllCourses() {
        final List<CourseEntity> courseEntities = GenerateMany.generateListOf(
                () -> randomCourseEntityBuilder().build(),
                getRandomNumberBetween(0,20));
        final List<Course> expectedCourses = courseEntities.stream()
                .map(CourseEntityToCourseMapper::mapCourseEntityToCourse)
                .collect(toList());
        when(courseDataAccessService.getAllCourses()).thenReturn(courseEntities);

        final List<Course> actualCourses = courseService.getAllCourses();
        verify(courseDataAccessService).getAllCourses();

        final List<String> expectedIds = expectedCourses
                .stream().map(Course::getId).collect(toList());
        actualCourses.forEach(
                x -> assertThat(x.getId()).isIn(expectedIds));
    }

    @Test
    void addSession_shouldCallAddSessionToCourse_andCreateNewSession_andReturnSessionId(){
        final String courseId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String sessionId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String sessionName = getRandomAlphaNumericString(getRandomNumberBetween(5,20));

        when(sessionService.createSession(sessionName)).thenReturn(sessionId);
        doNothing().when(courseDataAccessService).addSessionToCourse(courseId, sessionId);

        final String actualSessionId = courseService.addSession(courseId, sessionName);

        verify(courseDataAccessService).addSessionToCourse(courseId,sessionId);
        assertThat(sessionId).isEqualTo(actualSessionId);
    }

    @Test
    void getSessions_shouldCallGetSessions_andReturnSessions(){
        final List<String> sessionIds = GenerateMany.generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(0,10));
        final List<Session> sessions = sessionIds
                .stream()
                .map(x -> RandomSession.getRandomSessionBuilder().id(x).build())
                .collect(toList());

        when(sessionService.getSessions(any())).thenReturn(sessions);

        final List<Session> actualSessions = courseService.getSessions(sessionIds);

        actualSessions.forEach(
                x -> assertThat(x.getId()).isIn(sessionIds));
    }
}
