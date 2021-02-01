package com.education.education.web;

import com.education.education.course.CourseService;
import com.education.education.web.models.CourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@RequestMapping("/course")
@EnableWebMvc // TODO: What does this do
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(final CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourse(@RequestBody final CourseRequest courseRequest){
        courseService.createCourse(courseRequest.getCourseName());
    }
}
