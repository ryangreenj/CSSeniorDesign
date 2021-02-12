package com.education.education.profile.repositories.entities.mappers;

import com.education.education.profile.Profile;
import com.education.education.profile.repositories.entities.ProfileEntity;
import org.junit.jupiter.api.Test;

import static com.education.education.profile.helpers.RandomProfileEntity.getRandomProfileEntityBuilder;
import static com.education.education.profile.repositories.entities.mappers.ProfileEntityToProfileMapper.mapProfileEntityToProfile;
import static org.assertj.core.api.Assertions.assertThat;

class ProfileEntityToProfileMapperTest {

    @Test
    void mapProfileEntityToProfile_shouldMapProfileEntityToProfile() {
        final ProfileEntity profileEntity = getRandomProfileEntityBuilder().build();
        final Profile profile = Profile.aProfileBuilder()
                .id(profileEntity.getId())
                .username(profileEntity.getUsername())
                .coursesEnrolled(profileEntity.getCoursesEnrolled())
                .coursesOwned(profileEntity.getCoursesOwned())
                .build();

        assertThat(mapProfileEntityToProfile(profileEntity)).isEqualToComparingFieldByField(profile);
    }
}
