package com.education.education.user;

import java.util.List;

public interface UserDataAccessService {

    void insertUser(final String username, final String password);

    List<User> getAllUsers();

    User getUser(String userId);
}
