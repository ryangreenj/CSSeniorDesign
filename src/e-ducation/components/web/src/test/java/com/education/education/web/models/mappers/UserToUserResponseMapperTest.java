package com.education.education.web.models.mappers;

import com.education.education.user.User;
import com.education.education.web.models.UserResponse;
import org.junit.jupiter.api.Test;

import static com.education.education.web.helpers.RandomUser.randomUser;
import static com.education.education.web.models.UserResponse.aUserResponseBuilder;
import static com.education.education.web.models.mappers.UserToUserResponseMapper.mapUserToUserResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserToUserResponseMapperTest {

    @Test
    void mapUserToUserResponse_shouldMapUserToUserResponse(){
        final User user = randomUser();
        final UserResponse expectedUserResponse = aUserResponseBuilder()
                .username(user.getUsername())
                .id(user.getId())
                .build();

        assertThat(mapUserToUserResponse(user)).isEqualToComparingFieldByField(expectedUserResponse);
    }
}
