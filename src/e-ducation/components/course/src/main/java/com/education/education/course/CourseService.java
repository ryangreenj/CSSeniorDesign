package com.education.education.course;

import com.education.education.course.models.SessionNotificationStudent;
import com.education.education.course.repositories.entities.mappers.CourseEntityToCourseMapper;
import com.education.education.promptlet.PROMPTLET_TYPE;
import com.education.education.promptlet.Promptlet;
import com.education.education.promptlet.UserResponse;
import com.education.education.session.Session;
import com.education.education.session.SessionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.education.education.course.models.PromptletNotificationStudent.aPromptletNotificationStudentBuilder;

@Service
public class CourseService {

    private final CourseDataAccessService courseDataAccessService;

    private final SessionService sessionService;

    private final SimpMessagingTemplate template;

    public CourseService(
            @Qualifier("MongoCourseDataAccessService") final CourseDataAccessService courseDataAccessService,
            final SessionService sessionService, final SimpMessagingTemplate template) {
        this.courseDataAccessService = courseDataAccessService;
        this.sessionService = sessionService;
        this.template = template;
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
        final String oldSessionId = courseDataAccessService.setActiveSession(courseId,sessionId);
        notifyWebClientStudentOfNewActiveSession(courseId, oldSessionId, sessionId);
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
        final String promptletId = sessionService.addPromptletToSession(sessionId, prompt, PROMPTLET_TYPE.fromString(promptlet_type), answerPool, correctAnswer);
        notifyWebClientStudentOfPromptlet(sessionId, promptletId, prompt, promptlet_type, answerPool);
        return promptletId;
    }

    public List<Promptlet> getPromptlets(final List<String> promptletIds){
        return sessionService.getPromptlets(promptletIds);
    }

    public void answerPromptlet(final String promptletId, final String profileId,final String profileName, final List<String> response){
        sessionService.answerPromptlet(promptletId, profileId, profileName, response);
    }

    public List<UserResponse> getPromptletResponses(final List<String> responseIds){
        return sessionService.getPromptletResponses(responseIds);
    }

    private void notifyWebClientStudentOfPromptlet(final String sessionId, final String id, final String prompt, final String promptletType, final List<String> responsePool){
        template.convertAndSend("/topic/notification/" + sessionId, aPromptletNotificationStudentBuilder()
                .id(id).prompt(prompt).promptletType(promptletType).responsePool(responsePool).correctAnswer(new ArrayList<>())
                .userResponses(new ArrayList<>()).build());
    }

    private void notifyWebClientStudentOfNewActiveSession(final String courseId, final String oldSessionId, final String newSessionId){
        String currentSocket = oldSessionId;
        if (oldSessionId.equals("")){
            currentSocket = courseId;
        }
        template.convertAndSend("/topic/notification/" + currentSocket, SessionNotificationStudent.aSessionNotificationStudentBuilder()
                .newSessionId(newSessionId).build());
    }

    public void activatePromptlet(final String promptletId, final boolean status) {
        sessionService.activatePromptlet(promptletId, status);
    }
}
