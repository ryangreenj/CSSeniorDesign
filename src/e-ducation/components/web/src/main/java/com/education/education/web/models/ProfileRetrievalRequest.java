package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileRetrievalRequest {

    private final String profileId;

    public ProfileRetrievalRequest(
            @JsonProperty("id") final String profileId
    ) {
        this.profileId = profileId;
    }
}
