package com.education.education.promptlet;

import java.util.List;

public interface PromptletDataAccessService {

    String createPromptlet(
            final String prompt,
            final PROMPTLET_TYPE promptletType,
            final List<String> responsePool,
            final List<String> correctAnswer);

    List<Promptlet> getPromptlets(final List<String> promptletId);

    UserResponse answerPromptlet(final String promptletId, final String profileId, final List<String> response);

    List<UserResponse> getPromptletResponse(final List<String> responseIds);

    void activatePromptlet(final String promptletId, final boolean status);
}
