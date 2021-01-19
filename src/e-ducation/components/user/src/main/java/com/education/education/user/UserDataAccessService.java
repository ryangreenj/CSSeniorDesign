package com.education.education.user;

import java.util.List;

public interface UserDataAccessService {

    void insertUser(String username, String password);

    List<User> getAllUsers();

    User getUser(String userId);
}
