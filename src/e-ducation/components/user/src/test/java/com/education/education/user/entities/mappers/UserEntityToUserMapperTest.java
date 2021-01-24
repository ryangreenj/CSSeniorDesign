package com.education.education.user.entities.mappers;

import com.education.education.user.User;
import com.education.education.user.entities.UserEntity;
import com.education.education.user.helpers.RandomUser;
import com.education.education.user.helpers.RandomUserEntity;
import org.junit.jupiter.api.Test;

import static com.education.education.user.User.aUserBuilder;
import static com.education.education.user.entities.mappers.UserEntityToUserMapper.mapUserEntityToUser;
import static com.education.education.user.helpers.RandomUserEntity.randomUserEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
