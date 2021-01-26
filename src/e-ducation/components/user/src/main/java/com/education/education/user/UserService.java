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
    public UserService(@Qualifier("MongoUserDataAccessService") final UserDataAccessService userDataAccessService) {
        this.userDataAccessService = userDataAccessService;
    }

    @Override
    public UserDetails loadUserByUsername( final String username) throws UsernameNotFoundException {
        try{
            return userDataAccessService.getUser(username);
        } catch (UserDataFailure userDataFailure) {
            throw new UsernameNotFoundException(userDataFailure.getMessage());
        }
    }

    public User getUser( final String username){
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
