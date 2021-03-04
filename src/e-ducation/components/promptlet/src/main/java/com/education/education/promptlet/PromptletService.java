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

    public void answerPromptlet(final String promptletId, final String profileId, final String profileName, final List<String> response){
        final String id = promptletDataAccessService.answerPromptlet(promptletId,profileId,response);
        notifyOwnerOfPromptletResponse(promptletId, id, profileId, profileName, response);
    }

    public List<UserResponse> getPromptletResponses(final List<String> responseIds){
        return promptletDataAccessService.getPromptletResponse(responseIds);
    }

    public void notifyOwnerOfPromptletResponse(final String promptletId, final String id, final String profileId, final String profileName, final List<String> response){

        template.convertAndSend("/topic/notification/" + promptletId, aPromptletNotificationOwnerBuilder()
                .id(id).profileId(profileId).profileName(profileName).responses(response).build());
    }

    public void activatePromptlet(final String promptletId, final boolean status) {
        promptletDataAccessService.activatePromptlet(promptletId, status);
    }
}
