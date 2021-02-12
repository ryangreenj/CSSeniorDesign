package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileCreationRequest {

    private final String username;

    public ProfileCreationRequest(
            @JsonProperty("username") final String username) {
        this.username = username;
    }
}
