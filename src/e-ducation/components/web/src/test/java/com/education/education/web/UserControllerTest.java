package com.education.education.web;

import com.education.education.user.User;
import com.education.education.user.UserService;
import com.education.education.web.helpers.RandomUser;
import com.education.education.web.models.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.testerhelper.GenerateMany.generateListOf;
import static com.education.education.user.UserDataFailure.failureToSaveUser;
import static com.education.education.web.helpers.RandomUser.randomUserRequest;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void insertUser_shouldReturnWithCreated_andCallInsertUser() throws Exception {
        final UserRequest userRequest = randomUserRequest();

        this.mockMvc.perform(post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userRequest)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).createUser(userRequest.getUsername(), userRequest.getPassword());
    }

    @Test
    void insertUser_shouldReturnWithServiceUnavailable_whenUserSaveFailureIsThrown() throws Exception {
        final UserRequest userRequest = randomUserRequest();

        final String exceptionMessage = getRandomAlphaNumericString(getRandomNumberBetween(5,25));
        doThrow(failureToSaveUser(exceptionMessage)).when(userService)
                .createUser(userRequest.getUsername(),userRequest.getPassword());

        this.mockMvc.perform(post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userRequest)))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    void getUsers_shouldReturnWithOk_andReturnAllUsers() throws Exception {
        final List<User> users = generateListOf(RandomUser::randomUser, getRandomNumberBetween(2,4));

        when(userService.getAllUsers()).thenReturn(users);
        this.mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(users)));

        verify(userService, times(1)).getAllUsers();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
