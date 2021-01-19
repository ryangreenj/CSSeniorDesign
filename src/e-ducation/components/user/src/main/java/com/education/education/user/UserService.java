package com.education.education.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserDataAccessService userDataAccessService;
    @Autowired
    public UserService(@Qualifier("MockUserDataAccessService") UserDataAccessService userDataAccessService)
    {
        this.userDataAccessService = userDataAccessService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDataAccessService.getUser(username);
    }

    public void createUser(final String username, final String password){
        userDataAccessService.insertUser(username, password);
    }

    // TODO - this for testing only, remove before deploy
    public List<User> getAllUsers (){
        return userDataAccessService.getAllUsers();
    }
}
