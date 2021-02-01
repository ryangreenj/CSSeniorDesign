package com.education.education.course;

import com.education.education.course.repositories.CourseRepository;
import com.education.education.course.repositories.entities.CourseEntity;
import com.mongodb.MongoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.education.education.course.helpers.RandomCourseEntity.randomCourseEntity_new;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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

    @Test
    void insertCourse_shouldThrowCourseDataFailure_whenMongoThrowsException() {
        final CourseEntity courseEntity = randomCourseEntity_new();
        final String exceptionMessage = getRandomAlphaNumericString(getRandomNumberBetween(5,50));

        doThrow(new MongoException(exceptionMessage)).when(courseRepository).save(any());
        assertThrows(CourseDataFailure.class,
                () -> mongoCourseDataAccessService.insertCourse(
                        courseEntity.getClassName()));
    }
}
