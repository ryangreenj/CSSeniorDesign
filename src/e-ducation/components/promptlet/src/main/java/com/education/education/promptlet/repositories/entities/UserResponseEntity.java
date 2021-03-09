package com.education.education.promptlet.repositories.entities;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Getter
@Document(collection = "UserResponses")
public class UserResponseEntity {

    private final String id;
    private final String profileId;
    private final List<String> response;
    private final long timestamp;

    public static UserResponseEntityBuilder aUserResponseEntityBuilder(){
        return UserResponseEntity.builder();
    }
}
