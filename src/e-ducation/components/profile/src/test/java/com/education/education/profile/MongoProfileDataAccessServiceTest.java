package com.education.education.profile;

import com.education.education.profile.helpers.RandomProfileEntity;
import com.education.education.profile.repositories.ProfileRepository;
import com.education.education.profile.repositories.entities.ProfileEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.education.education.profile.repositories.entities.mappers.ProfileEntityToProfileMapper.mapProfileEntityToProfile;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MongoProfileDataAccessServiceTest.class})
class MongoProfileDataAccessServiceTest {

    @MockBean
    private ProfileRepository profileRepository;

    private MongoProfileDataAccessService mongoProfileDataAccessService;

    @BeforeEach
    void setup(){
        this.mongoProfileDataAccessService = new MongoProfileDataAccessService(profileRepository);
    }

    @Test
    void createProfile_shouldCallSave_andReturnId() {
        final ProfileEntity profileEntity = RandomProfileEntity.getRandomProfileEntityBuilder().build();

        when(profileRepository.save(any())).thenReturn(profileEntity);

        final String actualProfileId = mongoProfileDataAccessService.createProfile(profileEntity.getUsername());

        verify(profileRepository).save(any());
        assertThat(actualProfileId).isEqualTo(profileEntity.getId());
    }

    @Test
    void createNewCourse_shouldGetExistingEntity_andAddCourseToExistingEntity_andSaveEntity() {
        final String profileId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String courseId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final ProfileEntity profileEntity = RandomProfileEntity.getRandomProfileEntityBuilder().id(profileId).build();

        when(profileRepository.findProfileEntityById(profileId)).thenReturn(profileEntity);

        mongoProfileDataAccessService.createNewCourse(profileId, courseId);

        verify(profileRepository).findProfileEntityById(profileId);
        profileEntity.getCoursesOwned().add(courseId);
        verify(profileRepository).save(profileEntity);

    }

    @Test
    void joinCourse_shouldGetExistingEntity_andAddCourseToExistingEntity_andSaveEntity() {
        final String profileId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String courseId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final ProfileEntity profileEntity = RandomProfileEntity.getRandomProfileEntityBuilder().id(profileId).build();

        when(profileRepository.findProfileEntityById(profileId)).thenReturn(profileEntity);

        mongoProfileDataAccessService.joinCourse(profileId, courseId);

        verify(profileRepository).findProfileEntityById(profileId);
        profileEntity.getCoursesEnrolled().add(courseId);
        verify(profileRepository).save(profileEntity);
    }

    @Test
    void getProfile_shouldFindProfileEntityById_andMapItToProfile() {
        final String profileId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final ProfileEntity profileEntity = RandomProfileEntity.getRandomProfileEntityBuilder().id(profileId).build();
        final Profile profile = mapProfileEntityToProfile(profileEntity);

        when(profileRepository.findProfileEntityById(profileId)).thenReturn(profileEntity);

        final Profile actualProfile = mongoProfileDataAccessService.getProfile(profileId);

        verify(profileRepository).findProfileEntityById(profileId);
        assertThat(actualProfile).isEqualToComparingFieldByField(profile);
    }
}
