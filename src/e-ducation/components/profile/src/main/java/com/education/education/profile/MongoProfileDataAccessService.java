package com.education.education.profile;

import com.education.education.course.repositories.CourseRepository;
import com.education.education.profile.repositories.ProfileRepository;
import com.education.education.profile.repositories.entities.ProfileEntity;
import com.education.education.profile.repositories.entities.mappers.ProfileEntityToProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import static com.education.education.profile.repositories.entities.mappers.ProfileEntityToProfileMapper.mapProfileEntityToProfile;

@Repository("MongoProfileDataAccessService")
public class MongoProfileDataAccessService implements ProfileDataAccessService{

    private ProfileRepository profileRepository;

    @Autowired
    public MongoProfileDataAccessService(final ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public String createProfile(String username) {
        final ProfileEntity profileEntity = ProfileEntity.aProfileEntityBuilder()
                .username(username)
                .coursesEnrolled(new ArrayList<>())
                .coursesOwned(new ArrayList<>())
                .build();
        return profileRepository.save(profileEntity).getId();
    }

    @Override
    public void createNewCourse(String profileId, String courseId) {
        final ProfileEntity profileEntity = profileRepository
                .findProfileEntityById(profileId);
        profileEntity.getCoursesOwned().add(courseId);
        profileRepository.save(profileEntity);
    }

    @Override
    public void joinCourse(String profileId, String courseId) {
        final ProfileEntity profileEntity = profileRepository
                .findProfileEntityById(profileId);
        profileEntity.getCoursesEnrolled().add(courseId);
        profileRepository.save(profileEntity);
    }

    @Override
    public Profile getProfile(String profileId) {
        return mapProfileEntityToProfile(profileRepository.findProfileEntityById(profileId));
    }
}
