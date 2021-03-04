package com.education.education.course.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SessionNotificationStudent {

    private final String newSessionId;
    private final String notificationType;

    public static SessionNotificationStudentBuilder aSessionNotificationStudentBuilder(){
        return SessionNotificationStudent.builder().notificationType("SESSION");
    }
}
