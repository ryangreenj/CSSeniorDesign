package com.education.education.session;

import lombok.Builder;

import java.util.List;

@Builder
public class Session {

    private final String id;
    private final String sessionName;
    private final List<Promptlet> promptlets;

    public static SessionBuilder aSessionBuilder(){
        return Session.builder();
    }
}
