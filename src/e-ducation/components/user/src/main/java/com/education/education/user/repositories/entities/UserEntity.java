package com.education.education.user.repositories.entities;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Builder
@Document(collection = "Users")
public class UserEntity {

    @Id
    private String id;
    private String username;
    private String password;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

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
