package com.education.education.profile.repositories.entities.mappers;

import com.education.education.profile.Profile;
import com.education.education.profile.repositories.entities.ProfileEntity;

import static com.education.education.profile.Profile.aProfileBuilder;

public class ProfileEntityToProfileMapper {

    public static Profile mapProfileEntityToProfile(final ProfileEntity profileEntity){
        return aProfileBuilder()
                .id(profileEntity.getId())
                .username(profileEntity.getUsername())
                .coursesOwned(profileEntity.getCoursesOwned())
                .coursesEnrolled(profileEntity.getCoursesEnrolled())
                .build();
    }
}
