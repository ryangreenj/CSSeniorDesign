package com.education.education.profile.repositories.entities;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Getter
@Document(collection = "Profile")
public class ProfileEntity {

    private final String id;
    private final String username;
    private final List<String> coursesEnrolled;
    private final List<String> coursesOwned;

    public static ProfileEntity.ProfileEntityBuilder aProfileEntityBuilder(){
        return ProfileEntity.builder();
    }
}
