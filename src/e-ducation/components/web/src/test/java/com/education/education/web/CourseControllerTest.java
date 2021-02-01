package com.education.education.web;

import com.education.education.course.CourseService;
import com.education.education.web.models.CourseCreationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.education.education.testerhelper.JsonString.asJsonString;
import static com.education.education.web.helpers.RandomCourse.randomCourseRequest;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void createCourse_shouldReturnCreated_andCallCreateCourse() throws Exception {
        final CourseCreationRequest courseCreationRequest = randomCourseRequest();

        this.mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(courseCreationRequest)))
                .andExpect(status().isCreated());

        verify(courseService).createCourse(courseCreationRequest.getCourseName());
    }
}
