package com.education.education.course;

import com.education.education.course.repositories.CourseRepository;
import com.education.education.course.repositories.entities.CourseEntity;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.education.education.course.CourseDataFailure.failureToSaveCourse;
import static com.education.education.course.repositories.entities.CourseEntity.aCourseEntityBuilder;
import static java.util.stream.Collectors.toList;

@Repository("MongoCourseDataAccessService")
public class MongoCourseDataAccessService implements CourseDataAccessService{

    private final CourseRepository courseRepository;

    @Autowired
    public MongoCourseDataAccessService(final CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @Override
    public String insertCourse(final String className) {
        try{
            return courseRepository.save(
                    aCourseEntityBuilder()
                            .className(className)
                            .sessionIds(new ArrayList<>())
                            .build()).getId();
        } catch (MongoException mongoException){
            throw failureToSaveCourse(className);
        }
    }

    @Override
    public void addSessionToCourse(final String courseId, final String sessionId) {
        final CourseEntity courseEntity = courseRepository.findCourseEntityById(courseId);

        courseEntity.getSessionIds().add(sessionId);
        courseRepository.save(courseEntity);
    }

    @Override
    public List<CourseEntity> getCourses(final List<String> courseIds) {
        return courseIds
                .stream()
                .map(courseRepository::findCourseEntityById)
                .collect(toList());
    }

    @Override
    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }
}
