package com.education.education.web;

import com.education.education.course.Course;
import com.education.education.course.CourseService;
import com.education.education.testerhelper.GenerateMany;
import com.education.education.web.helpers.RandomSession;
import com.education.education.web.models.CourseCreationRequest;
import com.education.education.web.models.CourseRequest;
import com.education.education.web.models.CourseResponse;
import com.education.education.web.models.SessionRequest;
import com.education.education.web.models.mappers.CourseToCourseResponseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;

import static com.education.education.course.Course.aCourseBuilder;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.testerhelper.GenerateMany.generateListOf;
import static com.education.education.testerhelper.JsonString.asJsonString;
import static com.education.education.web.helpers.RandomCourse.randomCourseBuilder;
import static com.education.education.web.helpers.RandomCourse.randomCourseCreationRequest;
import static com.education.education.web.helpers.RandomCourse.randomCourseRequest;
import static com.education.education.web.helpers.RandomSession.randomSessionRequest;
import static com.education.education.web.models.mappers.CourseToCourseResponseMapper.mapCourseToCourseResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void createCourse_shouldReturnCreatedCode_andCallCreateCourse() throws Exception {
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

        this.mockMvc.perform(get("/course")
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

        this.mockMvc.perform(get("/course/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString("{}")))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(courseResponses)));

        verify(courseService).getAllCourses();
    }

    @Test
    void createSession_shouldReturnCreated_andCallAddSession() throws Exception {
        final SessionRequest sessionRequest = randomSessionRequest();

        this.mockMvc.perform(post("/course/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(sessionRequest)))
                .andExpect(status().isCreated());

        verify(courseService).addSession(sessionRequest.getCourseId(),sessionRequest.getSessionName());
    }
}
