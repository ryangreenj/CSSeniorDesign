package com.education.education.web.models;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Builder
public class UserResponse {

    private final String id;
    private final String username;

    public static UserResponseBuilder aUserResponseBuilder(){
        return UserResponse.builder();
    }
}
