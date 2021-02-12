package com.education.education.session.repositories;

import com.education.education.session.repositories.entities.SessionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends MongoRepository<SessionEntity, String> {

    SessionEntity findSessionEntityById(String id);
}
