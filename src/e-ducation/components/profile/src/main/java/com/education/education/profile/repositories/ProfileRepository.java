package com.education.education.profile.repositories;

import com.education.education.profile.repositories.entities.ProfileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<ProfileEntity, String> {

    ProfileEntity findProfileEntityById(final String profileId);
}
