package com.education.education.user.repositories.entities.mappers;

import com.education.education.user.User;
import com.education.education.user.repositories.entities.UserEntity;
import org.junit.jupiter.api.Test;

import static com.education.education.user.User.aUserBuilder;
import static com.education.education.user.helpers.RandomUserEntity.randomUserEntity;
import static com.education.education.user.repositories.entities.mappers.UserEntityToUserMapper.mapUserEntityToUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserEntityToUserMapperTest {

    @Test
    void mapUserEntityToUser_shouldMapUserEntityToUser() {
        final UserEntity userEntity = randomUserEntity();
        final User user = aUserBuilder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();


        assertThat(mapUserEntityToUser(userEntity)).isEqualToComparingFieldByField(user);
    }
}
