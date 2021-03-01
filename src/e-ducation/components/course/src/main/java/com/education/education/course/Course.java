package com.education.education.course;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Course {

    private final String id;
    private final String className;
    private final List<String> sessionIds;
    private final String activeSessionId;

    public static CourseBuilder aCourseBuilder(){
        return Course.builder();
    }
}
