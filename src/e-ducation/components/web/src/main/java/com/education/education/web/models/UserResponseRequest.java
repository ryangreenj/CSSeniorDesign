package com.education.education.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class UserResponseRequest {

    final List<String> userResponseIds;

    public UserResponseRequest(
            @JsonProperty("userResponseIds") List<String> userResponseIds) {
        this.userResponseIds = userResponseIds;
    }
}
