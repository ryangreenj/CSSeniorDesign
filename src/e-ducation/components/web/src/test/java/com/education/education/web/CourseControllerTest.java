package com.education.education.web;

import com.education.education.course.Course;
import com.education.education.course.CourseService;
import com.education.education.session.Session;
import com.education.education.web.models.CourseCreationRequest;
import com.education.education.web.models.CourseRequest;
import com.education.education.web.models.CourseResponse;
import com.education.education.web.models.SessionCreationRequest;
import com.education.education.web.models.SessionResponse;
import com.education.education.web.models.SessionRetrievalRequest;
import com.education.education.web.models.mappers.CourseToCourseResponseMapper;
import com.education.education.web.models.mappers.SessionToSessionResponseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.testerhelper.GenerateMany.generateListOf;
import static com.education.education.testerhelper.JsonString.asJsonString;
import static com.education.education.web.helpers.RandomCourse.randomCourseBuilder;
import static com.education.education.web.helpers.RandomCourse.randomCourseCreationRequest;
import static com.education.education.web.helpers.RandomCourse.randomCourseRequest;
import static com.education.education.web.helpers.RandomSession.randomSessionBuilder;
import static com.education.education.web.helpers.RandomSession.randomSessionCreationRequest;
import static com.education.education.web.helpers.RandomSession.randomSessionRetrievalRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void createCourse_shouldReturnIdOfCreatedCourse_andCallCreateCourse() throws Exception {
        final CourseCreationRequest courseCreationRequest = randomCourseCreationRequest();

        this.mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(courseCreationRequest)))
                .andExpect(status().isCreated());

        verify(courseService).createCourse(courseCreationRequest.getCourseName());
    }

    @Test
    void getCourses_shouldReturnCourseResponse_andCallGetCourses() throws Exception {
        final CourseRequest courseCreationRequest = randomCourseRequest();
        final List<Course> courses = courseCreationRequest.getCourseIds()
                .stream()
                .map(x -> randomCourseBuilder().id(x).build())
                .collect(Collectors.toList());
        final List<CourseResponse> courseResponses = courses
                .stream()
                .map(CourseToCourseResponseMapper::mapCourseToCourseResponse)
                .collect(Collectors.toList());

        when(courseService.getCourses(any())).thenReturn(courses);

        this.mockMvc.perform(put("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(courseCreationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(courseResponses)));

        verify(courseService).getCourses(courseCreationRequest.getCourseIds());
    }

    @Test
    void getAllCourses_shouldReturnCourseResponse_andCallGetAllCourses() throws Exception {
        final List<Course> courses = generateListOf(
                () -> randomCourseBuilder().build(),
                getRandomNumberBetween(0,6));
        final List<CourseResponse> courseResponses = courses
                .stream()
                .map(CourseToCourseResponseMapper::mapCourseToCourseResponse)
                .collect(Collectors.toList());

        when(courseService.getAllCourses()).thenReturn(courses);

        this.mockMvc.perform(put("/course/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString("{}")))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(courseResponses)));

        verify(courseService).getAllCourses();
    }

    @Test
    void createSession_shouldReturnCreated_andCallAddSession() throws Exception {
        final SessionCreationRequest sessionCreationRequest = randomSessionCreationRequest();

        this.mockMvc.perform(post("/course/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(sessionCreationRequest)))
                .andExpect(status().isCreated());

        verify(courseService).addSession(sessionCreationRequest.getCourseId(), sessionCreationRequest.getSessionName());
    }

    @Test
    void getSessions_shouldReturnSessionResponseList_andCallGetSessions() throws Exception {
        final SessionRetrievalRequest sessionRetrievalRequest = randomSessionRetrievalRequest();
        final List<Session> sessions = sessionRetrievalRequest.getSessionIds()
                .stream()
                .map(x -> randomSessionBuilder().id(x).build())
                .collect(Collectors.toList());
        final List<SessionResponse> sessionResponses = sessions
                .stream()
                .map(SessionToSessionResponseMapper::mapSessionToSessionResponse)
                .collect(Collectors.toList());

        when(courseService.getSessions(sessionRetrievalRequest.getSessionIds())).thenReturn(sessions);
        this.mockMvc.perform(put("/course/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(sessionRetrievalRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(sessionResponses)));

        verify(courseService).getSessions(sessionRetrievalRequest.getSessionIds());
    }
}
