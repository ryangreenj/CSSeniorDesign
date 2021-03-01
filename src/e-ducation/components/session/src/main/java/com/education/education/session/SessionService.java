package com.education.education.session;

import com.education.education.promptlet.PROMPTLET_TYPE;
import com.education.education.promptlet.Promptlet;
import com.education.education.promptlet.PromptletService;
import com.education.education.session.repositories.entities.mappers.SessionEntityToSessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class SessionService {

    private final SessionDataAccessService sessionDataAccessService;

    private final PromptletService promptletService;

    private SimpMessagingTemplate template;

    @Autowired
    public SessionService(@Qualifier("MongoSessionDataAccessService") final SessionDataAccessService sessionDataAccessService,
                          final PromptletService promptletService, final SimpMessagingTemplate template) {
        this.sessionDataAccessService = sessionDataAccessService;
        this.promptletService = promptletService;
        this.template = template;
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
        notifyWebClientStudent(sessionId, promptletId, prompt, promptlet_type.toString(), answerPool);
        return promptletId;
    }

    public List<Promptlet> getPromptlets(final List<String> promptletIds){
        return promptletService.getPromptlets(promptletIds);
    }

    public void notifyWebClientStudent(final String sessionId, final String id, final String prompt, final String promptletType, final List<String> responsePool){
        System.out.println(sessionId);
        template.convertAndSend("/topic/notification/" + sessionId, PromptletNotificationStudent.aPromptletNotificationStudentBuilder()
                .id(id).prompt(prompt).promptletType(promptletType).responsePool(responsePool).correctAnswer(new ArrayList<>())
                .userResponses(new ArrayList<>()).build());
    }

    public void answerPromptlet(final String promptletId, final String profileId, final List<String> response){
        promptletService.answerPromptlet(promptletId, profileId, response);
    }
}
