package com.education.education.web;

import com.education.education.course.Course;
import com.education.education.course.CourseService;
import com.education.education.web.models.CourseCreationRequest;
import com.education.education.web.models.CourseRequest;
import com.education.education.web.models.CourseResponse;
import com.education.education.web.models.SessionRequest;
import com.education.education.web.models.mappers.CourseToCourseResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.stream.Collectors;

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

    /**  MAKE CUSTOM RETURN TYPE  **/
    @GetMapping("/all")
    public List<CourseResponse> getCourses(){
        return courseService.getAllCourses()
                .stream()
                .map(CourseToCourseResponseMapper::mapCourseToCourseResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("")
    public List<CourseResponse> getCourses(@RequestBody final CourseRequest courseRequest){
        return courseService.getCourses(courseRequest.getCourseIds())
                .stream()
                .map(CourseToCourseResponseMapper::mapCourseToCourseResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/session")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSession(@RequestBody final SessionRequest sessionRequest){
        courseService.addSession(sessionRequest.getCourseId(), sessionRequest.getSessionName());
    }
}
