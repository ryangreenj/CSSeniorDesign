package com.education.education.web;

import com.education.education.course.CourseService;
import com.education.education.web.models.CourseCreationRequest;
import com.education.education.web.models.CourseRequest;
import com.education.education.web.models.CourseResponse;
import com.education.education.web.models.PromptletCreationRequest;
import com.education.education.web.models.PromptletRetrievalRequest;
import com.education.education.web.models.PromptletRetrievalResponse;
import com.education.education.web.models.SessionCreationRequest;
import com.education.education.web.models.SessionResponse;
import com.education.education.web.models.SessionRetrievalRequest;
import com.education.education.web.models.mappers.CourseToCourseResponseMapper;
import com.education.education.web.models.mappers.PromptletToPromptletRetrievalResponseMapper;
import com.education.education.web.models.mappers.SessionToSessionResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/course")
@EnableWebMvc // TODO: What does this do
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(final CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourse(@RequestBody final CourseCreationRequest courseCreationRequest){
        courseService.createCourse(courseCreationRequest.getCourseName());
    }

    @GetMapping("")
    public List<CourseResponse> getCourses(@RequestBody final CourseRequest courseRequest){
        return courseService.getCourses(courseRequest.getCourseIds())
                .stream()
                .map(CourseToCourseResponseMapper::mapCourseToCourseResponse)
                .collect(toList());
    }

    @GetMapping("/all")
    public List<CourseResponse> getAllCourses(){
        return courseService.getAllCourses()
                .stream()
                .map(CourseToCourseResponseMapper::mapCourseToCourseResponse)
                .collect(toList());
    }

    @PostMapping("/session")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSession(@RequestBody final SessionCreationRequest sessionCreationRequest){
        courseService.addSession(sessionCreationRequest.getCourseId(), sessionCreationRequest.getSessionName());
    }

    @GetMapping("/session")
    public List<SessionResponse> getSession(@RequestBody final SessionRetrievalRequest sessionRetrievalRequest){
        return courseService.getSessions(sessionRetrievalRequest.getSessionIds())
                .stream()
                .map(SessionToSessionResponseMapper::mapSessionToSessionResponse)
                .collect(toList());
    }

    @PostMapping("/session/promptlet")
    public String createPromptlet(@RequestBody final PromptletCreationRequest promptletCreationRequest){
        return courseService.addPromptletToSession(
                promptletCreationRequest.getSessionId(),
                promptletCreationRequest.getPrompt(),
                promptletCreationRequest.getPromptlet_type(),
                promptletCreationRequest.getAnswerPool(),
                promptletCreationRequest.getCorrectAnswer());
    }

    @GetMapping("/session/promptlet")
    public List<PromptletRetrievalResponse> getPromptlets(@RequestBody final PromptletRetrievalRequest promptletRetrievalRequest){
        return courseService.getPromptlets(promptletRetrievalRequest.getPromptletIds())
                .stream()
                .map(PromptletToPromptletRetrievalResponseMapper::mapPromptletToPromptletRetrievalResponse)
                .collect(toList());
    }
}
