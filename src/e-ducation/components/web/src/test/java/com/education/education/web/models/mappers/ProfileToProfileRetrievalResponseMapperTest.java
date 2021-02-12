package com.education.education.web.models.mappers;

import com.education.education.profile.Profile;
import com.education.education.web.models.ProfileRetrievalResponse;
import org.junit.jupiter.api.Test;

import static com.education.education.web.helpers.RandomProfile.getRandomProfileBuilder;
import static com.education.education.web.models.mappers.ProfileToProfileRetrievalResponseMapper.mapProfileToProfileRetrievalResponse;
import static org.assertj.core.api.Assertions.assertThat;

class ProfileToProfileRetrievalResponseMapperTest {

    @Test
    void mapProfileToProfileRetrievalResponse_shouldMapProfileToProfileRetrievalResponse() {
        final Profile profile = getRandomProfileBuilder().build();
        final ProfileRetrievalResponse profileRetrievalResponse = ProfileRetrievalResponse.aProfileRetrievalResponseBuilder()
                .id(profile.getId())
                .username(profile.getUsername())
                .coursesOwned(profile.getCoursesOwned())
                .coursesEnrolled(profile.getCoursesEnrolled())
                .build();

        assertThat(mapProfileToProfileRetrievalResponse(profile)).isEqualToComparingFieldByField(profileRetrievalResponse);
    }
}
