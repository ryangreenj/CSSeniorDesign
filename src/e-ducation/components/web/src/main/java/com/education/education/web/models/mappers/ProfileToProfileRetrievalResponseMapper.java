package com.education.education.web.models.mappers;

import com.education.education.profile.Profile;
import com.education.education.web.models.ProfileRetrievalResponse;

public class ProfileToProfileRetrievalResponseMapper {

    public static ProfileRetrievalResponse mapProfileToProfileRetrievalResponse(final Profile profile){
        return ProfileRetrievalResponse.aProfileRetrievalResponseBuilder()
                .id(profile.getId())
                .username(profile.getUsername())
                .coursesOwned(profile.getCoursesOwned())
                .coursesEnrolled(profile.getCoursesEnrolled())
                .build();
    }
}
