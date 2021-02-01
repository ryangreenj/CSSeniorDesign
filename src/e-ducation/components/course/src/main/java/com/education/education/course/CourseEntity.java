package com.education.education.course;

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
    private String id;
    private String className;
    private List<String> sessionIds;

    public static CourseEntityBuilder aCourseEntityBuilder()
    {
        return CourseEntity.builder();
    }
}
