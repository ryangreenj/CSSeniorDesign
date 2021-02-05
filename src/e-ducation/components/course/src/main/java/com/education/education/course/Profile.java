package com.education.education.course;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Profile {

    private final String id;
    private final String username;
    private final List<Course> coursesEnrolled;
    private final List<Course> coursesOwned;

}
