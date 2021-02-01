package com.education.education.web.models;

import com.education.education.session.Session;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CourseResponse {

    private final String id;
    private final String className;
    private final List<String> sessionIds;

    public static CourseResponseBuilder aCourseResponseBuilder(){
        return CourseResponse.builder();
    }
}
