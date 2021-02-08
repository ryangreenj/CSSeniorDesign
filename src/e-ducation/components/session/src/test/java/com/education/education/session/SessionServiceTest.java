package com.education.education.session;

import com.education.education.session.helpers.RandomSessionEntity;
import com.education.education.session.repositories.entities.SessionEntity;
import com.education.education.session.repositories.entities.mappers.SessionEntityToSessionMapper;
import com.education.education.testerhelper.Chance;
import com.education.education.testerhelper.GenerateMany;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Collectors;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {SessionService.class})
class SessionServiceTest {

    @MockBean
    @Qualifier("MongoSessionDataAccessService")
    private SessionDataAccessService sessionDataAccessService;

    private SessionService sessionService;

    @BeforeEach
    public void setup(){
        this.sessionService = new SessionService(sessionDataAccessService);
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
}
