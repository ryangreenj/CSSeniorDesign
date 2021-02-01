package com.education.education.course.repositories;

import com.education.education.course.repositories.entities.CourseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<CourseEntity, String> {

    CourseEntity findCourseEntityById(final String courseId);

}
