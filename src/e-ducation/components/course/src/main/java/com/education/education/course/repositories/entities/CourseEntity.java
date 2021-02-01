package com.education.education.course.repositories.entities;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Builder
@Document(collection = "Courses")
@Getter
public class CourseEntity {

    @Id
    private final String id;
    private final String className;
    private final List<String> sessionIds;

    public static CourseEntityBuilder aCourseEntityBuilder()
    {
        return CourseEntity.builder();
    }
}
