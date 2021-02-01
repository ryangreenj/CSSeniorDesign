package com.education.education.user.repositories;

import com.education.education.user.repositories.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity findUserEntityByUsername(final String username);

    UserEntity findUserEntityById(final String Id);

    List<UserEntity> findAll();
}
