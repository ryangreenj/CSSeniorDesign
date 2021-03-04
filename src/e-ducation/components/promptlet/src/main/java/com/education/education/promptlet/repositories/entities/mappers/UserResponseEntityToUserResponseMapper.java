package com.education.education.promptlet.repositories.entities.mappers;

import com.education.education.promptlet.UserResponse;
import com.education.education.promptlet.repositories.entities.UserResponseEntity;

public class UserResponseEntityToUserResponseMapper {

    public static UserResponse mapUserResponseEntityToUserResponse(final UserResponseEntity userResponseEntity){
        return UserResponse.builder()
                .id(userResponseEntity.getId())
                .profileId(userResponseEntity.getProfileId())
                .response(userResponseEntity.getResponse())
                .build();
    }
}
