package com.education.education.promptlet.repositories;

import com.education.education.promptlet.repositories.entities.PromptletEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptletRepository extends MongoRepository<PromptletEntity, String> {

    PromptletEntity findPromptletEntityById(String id);
}
