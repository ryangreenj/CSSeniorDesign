package com.education.education.course;

import lombok.Builder;

import java.util.List;

@Builder
public class Course {

    private final String id;
    private final String className;
    private final List<Session> sessions;

    public String getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public static CourseBuilder aCourseBuilder(){
        return Course.builder();
    }
}
