package com.education.education.session.repositories;

import com.education.education.session.repositories.entities.SessionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends MongoRepository<SessionEntity, String> {

    List<SessionEntity> findSessionEntitiesById(List<String> ids);
}
