package com.education.education.course;

import com.education.education.course.repositories.entities.CourseEntity;

import java.util.List;

public interface CourseDataAccessService {

    String insertCourse(final String className);

    void addSessionToCourse(final String courseId, final String SessionId);

    List<CourseEntity> getCourses(final List<String> courseIds);

    List<CourseEntity> getAllCourses();

    void setActiveSession(final String courseId, final String sessionId);
}
