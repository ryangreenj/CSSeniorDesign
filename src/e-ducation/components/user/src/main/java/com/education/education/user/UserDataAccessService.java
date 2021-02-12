package com.education.education.user;

import java.util.List;

public interface UserDataAccessService {

    String insertUser(final String username, final String password, final String profileId);

    List<User> getAllUsers();

    User getUser(String userId);
}
