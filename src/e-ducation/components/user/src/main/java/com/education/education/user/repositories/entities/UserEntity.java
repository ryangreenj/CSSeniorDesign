package com.education.education.user.repositories.entities;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Builder
@Getter
@Document(collection = "Users")
public class UserEntity {

    @Id
    private String id;
    private String username;
    private String password;
    private String profileId;


    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, username, password);
    }

    public static UserEntityBuilder aUserEntityBuilder(){
        return UserEntity.builder();
    }
}
