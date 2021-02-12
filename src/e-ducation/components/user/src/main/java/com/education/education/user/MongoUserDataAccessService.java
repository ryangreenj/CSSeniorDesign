package com.education.education.user;


import com.education.education.user.repositories.UserRepository;
import com.education.education.user.repositories.entities.UserEntity;
import com.education.education.user.repositories.entities.mappers.UserEntityToUserMapper;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.education.education.user.repositories.entities.UserEntity.aUserEntityBuilder;

@Repository("MongoUserDataAccessService")
public class MongoUserDataAccessService implements UserDataAccessService {

    private final UserRepository userRepository;

    @Autowired
    public MongoUserDataAccessService(final UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public String insertUser(final String username, final String password, final String profileId){
        try{
            return userRepository.save(
                    aUserEntityBuilder().username(username).password(password).build()).getId();
        } catch (MongoException mongoException) {
            throw UserDataFailure.failureToSaveUser(mongoException.getMessage());
        }
    }

    // This one should probably be deleted
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(UserEntityToUserMapper::mapUserEntityToUser).collect(Collectors.toList());
    }

    @Override
    public User getUser(final String userId) {
        UserEntity userEntity = userRepository.findUserEntityById(userId);
        return userEntity != null ? UserEntityToUserMapper.mapUserEntityToUser(userEntity) : getUserByUsername(userId);
    }

    private User getUserByUsername(final String username){
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        return userEntity != null ? UserEntityToUserMapper.mapUserEntityToUser(userEntity) : null;
    }
}
