package com.education.education.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class UserDataFailure extends RuntimeException{

    private UserDataFailure(String message) {
        super(message);
    }

    public static UserDataFailure UsernameOrIdNotFound(final String user)
    {
        return new UserDataFailure("Failed to find username in user collection. | User: " + user);
    }

    public static UserDataFailure failureToSaveUser(final String message)
    {
        return new UserDataFailure("Failed to save user to mongo repository. | Message: " + message);
    }
}
