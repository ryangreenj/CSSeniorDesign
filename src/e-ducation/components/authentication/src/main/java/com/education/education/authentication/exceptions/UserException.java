package com.education.education.authentication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserException extends RuntimeException{

    private UserException(String message) {
        super(message);
    }

    public static UserException InvalidUserCredentials(String username){
        return new UserException("Invalid user credentials. | Username: " + username);
    }

    public static UserException invalidKeyWhenCreatingUser()
    {
        return new UserException("Invalid key when creating user");
    }

    public static UserException invalidKeyWhenAuthenticatingUser()
    {
        return new UserException("Invalid key when authenticating user:");
    }
}
