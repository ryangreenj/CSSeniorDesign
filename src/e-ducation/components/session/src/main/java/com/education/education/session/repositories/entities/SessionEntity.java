package com.education.education.session.repositories.entities;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Builder
@Getter
@Document(collection = "Sessions")
public class SessionEntity {

    @Id
    private final String id;
    private final String sessionName;
    private final List<String> promptlets;
    private final boolean isArchived;

    public static SessionEntityBuilder aSessionEntityBuilder(){
        return SessionEntity.builder();
    }
}

