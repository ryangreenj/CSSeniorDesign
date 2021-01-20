package com.education.education.user;

public class UserDataFailure extends RuntimeException{

    private UserDataFailure(String message) {
        super(message);
    }

    public static UserDataFailure UsernameOrIdNotFound(String user)
    {
        return new UserDataFailure("Failed to find username in user collection. | User: " + user);
    }

    public static UserDataFailure failureToSaveUser(String message)
    {
        return new UserDataFailure("Failed to save user to mongo repository. | Message: " + message);
    }
}
