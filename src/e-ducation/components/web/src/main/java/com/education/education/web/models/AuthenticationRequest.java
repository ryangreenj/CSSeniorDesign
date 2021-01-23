package com.education.education.web.models;

public class AuthenticationRequest {

    private final String username;
    private final String password;

    public AuthenticationRequest(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public AuthenticationRequest() {
        this.username = "";
        this.password = "";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
