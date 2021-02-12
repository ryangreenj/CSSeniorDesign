package com.education.education.profile;

import com.education.education.profile.helpers.RandomProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.education.education.profile.helpers.RandomProfile.getRandomProfileBuilder;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ProfileService.class})
class ProfileServiceTest {

    @MockBean
    @Qualifier("MongoProfileDataAccessService")
    private ProfileDataAccessService profileDataAccessService;

    private ProfileService profileService;

    @BeforeEach
    void setup(){
        this.profileService = new ProfileService(profileDataAccessService);
    }

    @Test
    void createProfile_shouldCallCreateProfile_andReturnProfileId() {
        final String username = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String profileId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));

        when(profileDataAccessService.createProfile(username)).thenReturn(profileId);

        final String actualProfileId = profileService.createProfile(username);

        verify(profileDataAccessService).createProfile(username);
        assertThat(actualProfileId).isEqualTo(profileId);
    }

    @Test
    void getProfile_shouldCallGetProfile_andReturnProfile() {
        final String profileId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final Profile profile = getRandomProfileBuilder().id(profileId).build();

        when(profileDataAccessService.getProfile(profileId)).thenReturn(profile);

        final Profile actualProfile = profileService.getProfile(profileId);

        verify(profileDataAccessService).getProfile(profileId);
        assertThat(actualProfile).isEqualToComparingFieldByField(profile);
    }

    @Test
    void joinClass_shouldCallJoinCourse() {
        final String profileId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String courseId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));

        profileService.joinClass(profileId, courseId);
        verify(profileDataAccessService).joinCourse(profileId,courseId);
    }

    @Test
    void createClass() {
        final String profileId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String courseId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));

        profileService.createClass(profileId, courseId);
        verify(profileDataAccessService).createNewCourse(profileId,courseId);
    }
}
