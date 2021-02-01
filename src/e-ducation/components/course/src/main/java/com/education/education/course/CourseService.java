package com.education.education.course;

import com.education.education.course.repositories.entities.mappers.CourseEntityToCourseMapper;
import com.education.education.session.SessionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.education.education.course.repositories.entities.mappers.CourseEntityToCourseMapper.mapCourseEntityToCourse;

@Service
public class CourseService {

    private final CourseDataAccessService courseDataAccessService;

    private final SessionService sessionService;

    public CourseService(
            @Qualifier("MongoCourseDataAccessService") final CourseDataAccessService courseDataAccessService,
            final SessionService sessionService) {
        this.courseDataAccessService = courseDataAccessService;
        this.sessionService = sessionService;
    }

    public void createCourse(final String courseName){
        courseDataAccessService.insertCourse(courseName);
    }

    public void addSession(final String courseId, final String sessionName){
        final String sessionId = sessionService.createSession(sessionName);
        courseDataAccessService.addSessionToCourse(courseId,sessionId);
    }

    public List<Course> getCourses(final List<String> courseIds) {
        return courseDataAccessService.getCourses(courseIds)
                .stream()
                .map(CourseEntityToCourseMapper::mapCourseEntityToCourse)
                .collect(Collectors.toList());
    }

    public List<Course> getAllCourses() {
        return courseDataAccessService.getAllCourses()
                .stream()
                .map(CourseEntityToCourseMapper::mapCourseEntityToCourse)
//                .map(x -> mapCourseEntityToCourse(x, new ArrayList<>()))
                .collect(Collectors.toList());
    }


}
