package com.education.education.user;

import com.education.education.user.entities.UserEntity;
import com.education.education.user.repositories.UserRepository;
import com.mongodb.MongoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.user.entities.mappers.UserEntityToUserMapper.mapUserEntityToUser;
import static com.education.education.user.helpers.RandomUserEntity.randomUserEntity;
import static com.education.education.user.helpers.RandomUserEntity.randomUserEntity_noId;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MongoUserDataAccessService.class})
class MongoUserDataAccessServiceTest {

    private MongoUserDataAccessService mongoUserDataAccessService;
    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        this.mongoUserDataAccessService = new MongoUserDataAccessService(userRepository);
    }

    @Test
    void insertUser_shouldSaveToUserRepository() {
        final UserEntity userEntity = randomUserEntity_noId();

        mongoUserDataAccessService.insertUser(userEntity.getUsername(), userEntity.getPassword());

        ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userEntityCaptor.capture());

        assertThat(userEntity).isEqualToComparingFieldByField(userEntityCaptor.getValue());
    }

    @Test
    void insertUser_shouldThrowUserDataFailure_whenMongoThrowsException() {
        final UserEntity userEntity = randomUserEntity();
        final String exceptionMessage = getRandomAlphaNumericString(getRandomNumberBetween(5,50));

        doThrow(new MongoException(exceptionMessage)).when(userRepository).save(any());
        assertThrows(UserDataFailure.class,
               () -> mongoUserDataAccessService.insertUser(
                       userEntity.getUsername(),
                       userEntity.getPassword()));
    }

    @Test
    void getUser_shouldReturnUserByUserId() {
        final UserEntity userEntity = randomUserEntity();
        final User expectedUser = mapUserEntityToUser(userEntity);

        when(userRepository.findUserEntityById(userEntity.getId())).thenReturn(userEntity);

        final User actualUser = mongoUserDataAccessService.getUser(userEntity.getId());
        assertThat(actualUser).isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    void getUser_shouldReturnUserByUserName() {
        final UserEntity userEntity = randomUserEntity();
        final User expectedUser = mapUserEntityToUser(userEntity);

        when(userRepository.findUserEntityById(userEntity.getUsername())).thenReturn(null);
        when(userRepository.findUserEntityByUsername(userEntity.getUsername())).thenReturn(userEntity);

        final User actualUser = mongoUserDataAccessService.getUser(userEntity.getUsername());
        assertThat(actualUser).isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    void getUser_shouldReturnNull_whenIdAndUsernameReturnNull() {
        final UserEntity userEntity = randomUserEntity();

        when(userRepository.findUserEntityById(userEntity.getUsername())).thenReturn(null);
        when(userRepository.findUserEntityByUsername(userEntity.getUsername())).thenReturn(null);

        final User actualUser = mongoUserDataAccessService.getUser(userEntity.getId());
        assertThat(actualUser).isNull();
    }
}