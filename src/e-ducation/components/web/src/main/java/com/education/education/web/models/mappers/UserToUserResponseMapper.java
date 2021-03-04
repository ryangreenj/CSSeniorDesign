package com.education.education.web.models.mappers;

import com.education.education.user.User;
import com.education.education.web.models.UserDataResponse;

import static com.education.education.web.models.UserDataResponse.aUserResponseBuilder;

public class UserToUserResponseMapper {

    public static UserDataResponse mapUserToUserResponse(final User user){
        return aUserResponseBuilder()
                .username(user.getUsername())
                .id(user.getId())
                .build();
    }
}
