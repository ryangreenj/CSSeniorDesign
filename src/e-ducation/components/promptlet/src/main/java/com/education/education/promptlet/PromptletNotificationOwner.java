package com.education.education.promptlet;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PromptletNotificationOwner {

    private final String id;
    private final String profileId;
    private final String profileName;
    private final List<String> responses;

    public static PromptletNotificationOwnerBuilder aPromptletNotificationOwnerBuilder(){
        return PromptletNotificationOwner.builder();
    }
}
