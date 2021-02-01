package com.education.education.course;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Promptlet {

    private final String id;
    private final String prompt;
    private final PROMPTLET_TYPE PROMPTLET_type;
    private final List<String> responsePool;
    private final List<String> correctAnswer;
    private final List<UserResponse> userResponses;
}
