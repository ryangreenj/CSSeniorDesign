package com.education.education.course;

import com.education.education.course.repositories.entities.mappers.CourseEntityToCourseMapper;
import com.education.education.promptlet.PROMPTLET_TYPE;
import com.education.education.promptlet.Promptlet;
import com.education.education.session.Session;
import com.education.education.session.SessionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public String createCourse(final String courseName){
        return courseDataAccessService.insertCourse(courseName);
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
                .collect(Collectors.toList());
    }

    public void setActiveSession(final String courseId, final String sessionId){
        courseDataAccessService.setActiveSession(courseId,sessionId);
    }

    public String addSession(final String courseId, final String sessionName){
        final String sessionId = sessionService.createSession(sessionName);
        courseDataAccessService.addSessionToCourse(courseId,sessionId);
        return sessionId;
    }

    public List<Session> getSessions(final List<String> sessionIds){
        return sessionService.getSessions(sessionIds);
    }

    public String addPromptletToSession(final String sessionId, final String prompt,
                                        final String promptlet_type, final List<String> answerPool,
                                        final List<String> correctAnswer){
        return sessionService.addPromptletToSession(sessionId, prompt, PROMPTLET_TYPE.fromString(promptlet_type), answerPool, correctAnswer);
    }

    public List<Promptlet> getPromptlets(final List<String> promptletIds){
        return sessionService.getPromptlets(promptletIds);
    }

    public void answerPromptlet(final String promptletId, final String profileId, final List<String> response){
        sessionService.answerPromptlet(promptletId, profileId, response);
    }
}
