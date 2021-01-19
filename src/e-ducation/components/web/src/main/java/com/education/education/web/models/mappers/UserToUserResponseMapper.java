package com.education.education.web.models.mappers;

import com.education.education.user.User;
import com.education.education.web.models.UserResponse;

import static com.education.education.web.models.UserResponse.aUserResponseBuilder;

public class UserToUserResponseMapper {

    public static UserResponse mapUserToUserResponse(User user){
        return aUserResponseBuilder()
                .username(user.getUsername())
                .id(user.getId())
                .build();
    }
}
