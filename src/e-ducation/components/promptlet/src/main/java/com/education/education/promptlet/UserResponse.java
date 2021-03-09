package com.education.education.promptlet;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserResponse {

    private final String id;
    private final String profileId;
    private final List<String> response;
    private final long timestamp;
}
