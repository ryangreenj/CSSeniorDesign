package com.education.education.session;

import com.education.education.promptlet.PROMPTLET_TYPE;
import com.education.education.promptlet.Promptlet;
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

    public static PromptletNotificationStudentBuilder aPromptletNotificationStudentBuilder(){
        return PromptletNotificationStudent.builder();
    }
}
