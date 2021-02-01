package com.education.education.course;

import com.education.education.course.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import static com.education.education.course.repositories.entities.CourseEntity.aCourseEntityBuilder;

@Repository("MongoCourseDataAccessService")
public class MongoCourseDataAccessService implements CourseDataAccessService{

    private final CourseRepository courseRepository;

    @Autowired
    public MongoCourseDataAccessService(final CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @Override
    public void insertCourse(final String className) {
        courseRepository.save(
                aCourseEntityBuilder()
                        .className(className)
                        .sessionIds(new ArrayList<>())
                        .build());
    }
}
