package com.education.education.promptlet;

import com.education.education.promptlet.repositories.entities.PromptletEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.education.education.promptlet.PromptletNotificationOwner.aPromptletNotificationOwnerBuilder;

@Service
public class PromptletService {

    private final PromptletDataAccessService promptletDataAccessService;

    private SimpMessagingTemplate template;

    @Autowired
    public PromptletService(@Qualifier("MongoPromptletDataAccessService") final PromptletDataAccessService promptletDataAccessService,
                            final SimpMessagingTemplate template) {
        this.promptletDataAccessService = promptletDataAccessService;
        this.template = template;
    }

    public String createPromptlet(final String prompt, final PROMPTLET_TYPE promptlet_type,
                                  final List<String> answerPool, final List<String> correctAnswer){
        return promptletDataAccessService.createPromptlet(prompt, promptlet_type, answerPool, correctAnswer);
    }

    public List<Promptlet> getPromptlets(final List<String> promptletIds){
        return promptletDataAccessService.getPromptlets(promptletIds);
    }

    public void answerPromptlet(final String activeSessionId,final String promptletId, final String profileId, final String profileName, final List<String> response){
        final UserResponse userResponse = promptletDataAccessService.answerPromptlet(promptletId,profileId,response);
        notifyOwnerOfPromptletResponse(activeSessionId, promptletId, userResponse.getId(), profileId, profileName, userResponse.getTimestamp(), response);
    }

    public List<UserResponse> getPromptletResponses(final List<String> responseIds){
        return promptletDataAccessService.getPromptletResponse(responseIds);
    }

    public void notifyOwnerOfPromptletResponse(final String activeSessionId, final String promptletId, final String id, final String profileId, final String profileName, final long timestamp, final List<String> response){
        template.convertAndSend("/topic/notification/" + activeSessionId, aPromptletNotificationOwnerBuilder()
                    .id(id).promptletId(promptletId).profileId(profileId).profileName(profileName)
                    .timestamp(timestamp).responses(response).build());
    }

    public void activatePromptlet(final String promptletId, final boolean status) {
        promptletDataAccessService.activatePromptlet(promptletId, status);
    }
}
