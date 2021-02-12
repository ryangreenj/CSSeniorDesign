package com.education.education.session;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Session {

    private final String id;
    private final String sessionName;
    private final List<String> promptletIds;

    public static SessionBuilder aSessionBuilder(){
        return Session.builder();
    }
}
