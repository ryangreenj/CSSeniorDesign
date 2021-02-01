package com.education.education.user.repositories.entities.mappers;


import com.education.education.user.User;
import com.education.education.user.repositories.entities.UserEntity;

public class UserEntityToUserMapper {

    public static User mapUserEntityToUser(UserEntity userEntity){
        return User.aUserBuilder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .id(userEntity.getId())
                .build();
    }
}
