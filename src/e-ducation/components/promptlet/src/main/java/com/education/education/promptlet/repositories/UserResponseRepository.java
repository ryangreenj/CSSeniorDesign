package com.education.education.promptlet.repositories;

import com.education.education.promptlet.repositories.entities.UserResponseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResponseRepository  extends MongoRepository<UserResponseEntity, String> {

    UserResponseEntity findUserResponseEntityById(String id);
}
