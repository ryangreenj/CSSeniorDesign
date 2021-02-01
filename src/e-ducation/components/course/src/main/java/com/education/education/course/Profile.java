package com.education.education.course;

import lombok.Builder;

import java.util.List;

@Builder
public class Profile {

    private final String id;
    private final String username;
    private final boolean isTeacher;
    private final List<Course> coursesEnrolled;
    private final List<Course> coursesOwned;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public List<Course> getCoursesEnrolled() {
        return coursesEnrolled;
    }

    public List<Course> getCoursesOwned() {
        return coursesOwned;
    }
}
