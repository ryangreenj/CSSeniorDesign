package com.education.education.session.repositories.entities;

import com.education.education.session.Promptlet;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Builder
@Document(collection = "Sessions")
@Getter
public class SessionEntity {

    @Id
    private final String id;
    private final String sessionName;
    private final List<String> promptlets;

    public static SessionEntityBuilder aSessionEntityBuilder(){
        return SessionEntity.builder();
    }
}
