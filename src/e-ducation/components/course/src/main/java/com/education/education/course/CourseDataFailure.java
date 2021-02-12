package com.education.education.course;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class CourseDataFailure extends RuntimeException{

    private CourseDataFailure(String message) {
        super(message);
    }

    public static CourseDataFailure UsernameOrIdNotFound(final String user)
    {
        return new CourseDataFailure("Failed to find username in user collection. | User: " + user);
    }

    public static CourseDataFailure failureToSaveCourse(final String message)
    {
        return new CourseDataFailure("Failed to save course to mongo repository. | Message: " + message);
    }
}
