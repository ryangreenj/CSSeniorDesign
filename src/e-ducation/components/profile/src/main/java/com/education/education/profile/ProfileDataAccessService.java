package com.education.education.profile;

import com.education.education.profile.Profile;

public interface ProfileDataAccessService {

    String createProfile(final String username);

    void createNewCourse(final String profileId, final String courseId);
    void joinCourse(final String profileId, final String courseId);

    Profile getProfile(final String profileId);
}
