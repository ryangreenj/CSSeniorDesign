package com.education.education.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseDataAccessService courseDataAccessService;

    @Autowired
    public CourseService(@Qualifier("MongoCourseDataAccessService") final CourseDataAccessService courseDataAccessService){
        this.courseDataAccessService = courseDataAccessService;
    }

    public void createCourse(final String courseName){
        courseDataAccessService.insertCourse(courseName);
    }
}
