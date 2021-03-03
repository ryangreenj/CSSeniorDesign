package com.education.education.course.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PromptletNotificationStudent {

    private final String id;
    private final String prompt;
    private final String promptletType;
    private final List<String> responsePool;
    private final List<String> correctAnswer;
    private final List<String> userResponses;
    private final String notificationType;
    private final boolean visible;

    public static PromptletNotificationStudentBuilder aPromptletNotificationStudentBuilder(){
        return PromptletNotificationStudent.builder().notificationType("PROMPTLET");
    }
}
