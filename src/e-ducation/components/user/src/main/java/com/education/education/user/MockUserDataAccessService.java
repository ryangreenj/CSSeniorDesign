package com.education.education.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.education.education.user.UserDataFailure.UsernameOrIdNotFound;

@Repository("MockUserDataAccessService")
public class MockUserDataAccessService implements UserDataAccessService{

    private final static List<User> mockUserStore = new ArrayList<>();

    @Override
    public void insertUser(final String username, final String password) {
        mockUserStore.add(User.aUserBuilder().username(username).password(password).build());
    }

    @Override
    public List<User> getAllUsers() {
        return mockUserStore;
    }

    @Override
    public User getUser(final String userId) {
        return mockUserStore.stream()
                .filter(user -> user.getId().equals(userId) || user.getUsername().equals(userId))
                .findFirst()
                .orElseThrow(() -> UsernameOrIdNotFound(userId));
    }

}
