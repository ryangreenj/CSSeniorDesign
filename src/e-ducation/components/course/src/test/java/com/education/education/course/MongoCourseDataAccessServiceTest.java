package com.education.education.course;

import com.education.education.course.helpers.RandomCourseEntity;
import com.education.education.course.repositories.CourseRepository;
import com.education.education.course.repositories.entities.CourseEntity;
import com.education.education.testerhelper.GenerateMany;
import com.mongodb.MongoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.education.education.course.helpers.RandomCourseEntity.randomCourseEntity_new;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void getCourses_shouldCallFindCourseEntityByIdForEachId_andReturnCourseEntities() {
        final List<String> courseIds = GenerateMany.generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(0,10));
        final List<CourseEntity> courseEntities = courseIds
                .stream()
                .map(x -> RandomCourseEntity.randomCourseEntityBuilder().id(x).build())
                .collect(toList());

        courseEntities.forEach(x -> {
            when(courseRepository.findCourseEntityById(x.getId())).thenReturn(x);
        });

        final List<CourseEntity> actualCourseEntities = mongoCourseDataAccessService.getCourses(courseIds);

        actualCourseEntities.forEach(
                x -> assertThat(x.getId()).isIn(courseIds));
    }

    @Test
    void getAllCourse_shouldCallFindAll_andReturnCourseEntities() {
        final List<String> courseIds = GenerateMany.generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(0,10));
        final List<CourseEntity> courseEntities = courseIds
                .stream()
                .map(x -> RandomCourseEntity.randomCourseEntityBuilder().id(x).build())
                .collect(toList());

        when(courseRepository.findAll()).thenReturn(courseEntities);

        final List<CourseEntity> actualCourseEntities = mongoCourseDataAccessService.getAllCourses();

        actualCourseEntities.forEach(
                x -> assertThat(x.getId()).isIn(courseIds));
    }
}
