package com.education.education.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileDataAccessService profileDataAccessService;

    @Autowired
    public ProfileService(@Qualifier("MongoProfileDataAccessService") final ProfileDataAccessService profileDataAccessService) {
        this.profileDataAccessService = profileDataAccessService;
    }

    public String createProfile(final String username){
        return profileDataAccessService.createProfile(username);
    }

    public Profile getProfile(final String id){
        return profileDataAccessService.getProfile(id);
    }

    public void joinClass(final String profileId, final String courseId){
        profileDataAccessService.joinCourse(profileId, courseId);
    }

    public void createClass(final String profileId, final String courseId){
        profileDataAccessService.createNewCourse(profileId, courseId);
    }
}
