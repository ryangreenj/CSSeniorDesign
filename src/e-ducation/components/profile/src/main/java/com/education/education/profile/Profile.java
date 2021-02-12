package com.education.education.profile;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Profile {

    private final String id;
    private final String username;
    private final List<String> coursesEnrolled;
    private final List<String> coursesOwned;

    public static ProfileBuilder aProfileBuilder(){
        return Profile.builder();
    }
}
