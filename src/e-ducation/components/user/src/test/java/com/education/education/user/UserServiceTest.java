package com.education.education.user;

import com.education.education.user.helpers.RandomUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static com.education.education.user.helpers.RandomUser.randomUser;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.testerhelper.GenerateMany.generateListOf;
import static com.education.education.user.UserDataFailure.failureToSaveUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserService.class})
class UserServiceTest {

    @MockBean
    @Qualifier("MongoUserDataAccessService")
    private UserDataAccessService userDataAccessService;

    private UserService userService;

    @BeforeEach
    public void setup(){
        this.userService = new UserService(userDataAccessService);
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetailsFromUsername() {
        final User user = randomUser();

        when(userDataAccessService.getUser(user.getUsername())).thenReturn(user);
        final UserDetails actual = userService.loadUserByUsername(user.getUsername());

        verify(userDataAccessService, times(1)).getUser(user.getUsername());
        assertThat(actual).isEqualTo(user);
    }

    @Test
    void insertUser_shouldCallInsertUser() {
        final String username = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String password = getRandomAlphaNumericString(getRandomNumberBetween(5,20));

        doNothing().when(userDataAccessService).insertUser(username, password);

        userService.createUser(username,password);
        verify(userDataAccessService, times(1)).insertUser(username, password);
    }


    @Test
    void insertUser_shouldThrowUserDataFailure_whenInsertUserThrowsUserDataFailure() {
        final String exceptionMessage = getRandomAlphaNumericString(getRandomNumberBetween(5,50));

        final String username = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String password = getRandomAlphaNumericString(getRandomNumberBetween(5,20));

        doThrow(failureToSaveUser(exceptionMessage)).when(userDataAccessService).insertUser(username, password);

        assertThrows(UserDataFailure.class, () -> userService.createUser(username,password), "");
    }

    @Test
    void getAllUsers() {
        final List<User> users = generateListOf(RandomUser::randomUser, getRandomNumberBetween(2,4));

        when(userDataAccessService.getAllUsers()).thenReturn(users);
        final List<User> actual = userService.getAllUsers();

        verify(userDataAccessService, times(1)).getAllUsers();
        Assertions.assertThat(actual).isEqualTo(users);
    }

    @Test
    void getUser_shouldReturnCorrectUser() {
        final User user = randomUser();

        when(userDataAccessService.getUser(user.getId())).thenReturn(user);
        final User actual = userService.getUser(user.getId());

        verify(userDataAccessService, times(1)).getUser(user.getId());
        assertThat(actual).isEqualTo(user);
    }
}
