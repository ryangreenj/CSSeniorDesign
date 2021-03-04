package com.education.education.web.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CourseResponse {

    private final String id;
    private final String className;
    private final List<String> sessionIds;
    private final String activeSessionId;

    public static CourseResponseBuilder aCourseResponseBuilder(){
        return CourseResponse.builder();
    }
}
