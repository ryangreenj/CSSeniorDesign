package com.education.education.web.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SessionResponse {

    private final String id;
    private final String sessionName;
    private final List<String> promptletIds;

    public static SessionResponseBuilder aSessionResponseBuilder(){
        return SessionResponse.builder();
    }
}
