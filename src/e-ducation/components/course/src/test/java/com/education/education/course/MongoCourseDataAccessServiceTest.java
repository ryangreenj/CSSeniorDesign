package com.education.education.course;

import com.education.education.course.repositories.CourseRepository;
import com.education.education.course.repositories.entities.CourseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.education.education.course.helpers.RandomCourseEntity.randomCourseEntity_new;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {MongoCourseDataAccessService.class})
class MongoCourseDataAccessServiceTest {

    private MongoCourseDataAccessService mongoCourseDataAccessService;

    @MockBean
    private CourseRepository courseRepository;

    @BeforeEach
    public void setup(){
        this.mongoCourseDataAccessService = new MongoCourseDataAccessService(courseRepository);
    }

    @Test
    void insertCourse_shouldSaveToCourseRepository() {
        final CourseEntity courseEntity = randomCourseEntity_new();

        mongoCourseDataAccessService.insertCourse(courseEntity.getClassName());

        ArgumentCaptor<CourseEntity> courseEntityCaptor = ArgumentCaptor.forClass(CourseEntity.class);
        verify(courseRepository).save(courseEntityCaptor.capture());

        assertThat(courseEntity).isEqualToComparingFieldByField(courseEntityCaptor.getValue());
    }
}
