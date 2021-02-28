package com.education.education.session;

import com.education.education.promptlet.PROMPTLET_TYPE;
import com.education.education.promptlet.Promptlet;
import com.education.education.promptlet.PromptletService;
import com.education.education.session.helpers.RandomPromptlet;
import com.education.education.session.helpers.RandomSessionEntity;
import com.education.education.session.repositories.entities.SessionEntity;
import com.education.education.session.repositories.entities.mappers.SessionEntityToSessionMapper;
import com.education.education.testerhelper.GenerateMany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {SessionService.class})
class SessionServiceTest {

    @MockBean
    @Qualifier("MongoSessionDataAccessService")
    private SessionDataAccessService sessionDataAccessService;

    @MockBean
    private PromptletService promptletService;

    @MockBean
    private SimpMessagingTemplate template;

    private SessionService sessionService;

    @BeforeEach
    public void setup(){
        this.sessionService = new SessionService(sessionDataAccessService, promptletService,template);
    }

    @Test
    void createSession_shouldCallInsertSession_andReturnSessionId() {
        final String sessionName = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String sessionId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));

        when(sessionDataAccessService.insertSession(sessionName)).thenReturn(sessionId);

        final String actualSessionId = sessionService.createSession(sessionName);

        verify(sessionDataAccessService).insertSession(sessionName);
        assertThat(actualSessionId).isEqualTo(sessionId);
    }

    @Test
    void getSessions_shouldCallGetSessions_andReturnSessionsBasedOfID() {
        final List<String> sessionIds = GenerateMany.generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(0,10));
        final List<SessionEntity> sessionEntities = sessionIds.stream()
                .map(x -> RandomSessionEntity.getRandomSessionEntity().id(x).build())
                .collect(toList());
        final List<Session> sessions = sessionEntities.stream()
                .map(SessionEntityToSessionMapper::mapSessionEntityToSession)
                .collect(toList());

        when(sessionDataAccessService.getSessions(sessionIds)).thenReturn(sessionEntities);
        final List<Session> actualSessions = sessionService.getSessions(sessionIds);

        verify(sessionDataAccessService).getSessions(sessionIds);

        actualSessions.forEach(
                x -> assertThat(x.getId()).isIn(sessionIds));
    }

    @Test
    void addPromptletToSession_shouldCallCreatePromptlet_andAddPromptletToSession_andReturnPromptletId(){
        final String sessionId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String promptletId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String prompt = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final PROMPTLET_TYPE promptlet_type = PROMPTLET_TYPE.SLIDER;
        final List<String> answerPool = GenerateMany.generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(0,10));
        final List<String> correctAnswer = GenerateMany.generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(0,10));

        when(promptletService.createPromptlet(prompt, promptlet_type, answerPool, correctAnswer)).thenReturn(promptletId);
        doNothing().when(sessionDataAccessService).addPromptletToSession(sessionId, promptletId);

        final String actual = sessionService.addPromptletToSession(sessionId, prompt, promptlet_type, answerPool, correctAnswer);

        verify(promptletService).createPromptlet(prompt, promptlet_type, answerPool, correctAnswer);
        verify(sessionDataAccessService).addPromptletToSession(sessionId, promptletId);
        assertThat(actual).isEqualTo(promptletId);
    }

    @Test
    void getPromptlets_shouldCallGetPromptlets_andReturnPromptlets(){
        final List<String> promptletIds = GenerateMany.generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(0,10));
        final List<Promptlet> promptlets = promptletIds.stream()
                .map(x -> RandomPromptlet.getRandomPromptletBuilder().id(x).build())
                .collect(toList());

        when(promptletService.getPromptlets(promptletIds)).thenReturn(promptlets);
        final List<Promptlet> actualPromptlets = sessionService.getPromptlets(promptletIds);

        verify(promptletService).getPromptlets(promptletIds);

        actualPromptlets.forEach(
                x -> assertThat(x.getId()).isIn(promptletIds));
    }

}
