package com.education.education.session;

import com.education.education.promptlet.PROMPTLET_TYPE;
import com.education.education.promptlet.Promptlet;
import com.education.education.promptlet.PromptletService;
import com.education.education.promptlet.UserResponse;
import com.education.education.session.repositories.entities.mappers.SessionEntityToSessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class SessionService {

    private final SessionDataAccessService sessionDataAccessService;

    private final PromptletService promptletService;


    @Autowired
    public SessionService(@Qualifier("MongoSessionDataAccessService") final SessionDataAccessService sessionDataAccessService,
                          final PromptletService promptletService) {
        this.sessionDataAccessService = sessionDataAccessService;
        this.promptletService = promptletService;
    }

    public String createSession(final String sessionName){
        return sessionDataAccessService.insertSession(sessionName);
    }

    public List<Session> getSessions(final List<String> sessions){
        return sessionDataAccessService.getSessions(sessions)
                .stream()
                .map(SessionEntityToSessionMapper::mapSessionEntityToSession)
                .collect(toList());
    }

    public String addPromptletToSession(final String sessionId, final String prompt,
                                        final PROMPTLET_TYPE promptlet_type, final List<String> answerPool,
                                        final List<String> correctAnswer){
        final String promptletId = promptletService.createPromptlet(prompt, promptlet_type, answerPool, correctAnswer);
        sessionDataAccessService.addPromptletToSession(sessionId, promptletId);
        return promptletId;
    }

    public List<Promptlet> getPromptlets(final List<String> promptletIds){
        return promptletService.getPromptlets(promptletIds);
    }

    public void answerPromptlet(final String activeSessionId, final String promptletId, final String profileId,final String profileName, final List<String> response){
        promptletService.answerPromptlet(activeSessionId,promptletId, profileId,profileName, response);
    }

    public List<UserResponse> getPromptletResponses(final List<String> responseIds){
        return promptletService.getPromptletResponses(responseIds);
    }

    public void activatePromptlet(final String promptletId, final boolean status) {
        promptletService.activatePromptlet(promptletId, status);
    }
}
