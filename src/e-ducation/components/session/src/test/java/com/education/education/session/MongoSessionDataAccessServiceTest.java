package com.education.education.session;

import com.education.education.session.helpers.RandomSessionEntity;
import com.education.education.session.repositories.SessionRepository;
import com.education.education.session.repositories.entities.SessionEntity;
import com.education.education.testerhelper.GenerateMany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static com.education.education.session.helpers.RandomSessionEntity.getRandomSessionEntity;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MongoSessionDataAccessService.class})
class MongoSessionDataAccessServiceTest {

    @MockBean
    private SessionRepository sessionRepository;

    private MongoSessionDataAccessService mongoSessionDataAccessService;

    @BeforeEach
    public void setup(){
        this.mongoSessionDataAccessService = new MongoSessionDataAccessService(sessionRepository);
    }

    @Test
    void insertSession_shouldCreateNewSessionEntity_andSaveEntityToMongo_andReturnsID() {
        final String sessionName = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final SessionEntity sessionEntity = getRandomSessionEntity()
                .sessionName(sessionName)
                .promptlets(new ArrayList<>())
                .build();
        final ArgumentCaptor<SessionEntity> sessionEntityCaptor = ArgumentCaptor.forClass(SessionEntity.class);

        when(sessionRepository.save(any())).thenReturn(sessionEntity);
        final String actualSessionId = mongoSessionDataAccessService.insertSession(sessionName);

        verify(sessionRepository).save(sessionEntityCaptor.capture());
        assertThat(actualSessionId).isEqualTo(sessionEntity.getId());
        assertThat(sessionEntityCaptor.getValue().getSessionName()).isEqualTo(sessionName);
        assertThat(sessionEntityCaptor.getValue().getPromptlets()).isEqualTo(new ArrayList<>());
    }

    @Test
    void getSessions_shouldReturnAllSessionsById() {
        final List<String> sessionIds = GenerateMany.generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(0,10));
        final List<SessionEntity> sessionEntities = sessionIds.stream()
                .map(x -> getRandomSessionEntity().id(x).build())
                .collect(toList());

        sessionEntities.forEach(x -> when(sessionRepository.findSessionEntityById(x.getId())).thenReturn(x));

        final List<SessionEntity> actualSessions = mongoSessionDataAccessService.getSessions(sessionIds);

        actualSessions.forEach(x -> assertThat(x.getId()).isIn(sessionIds));

    }

    @Test
    void addPromptletToSession_shouldFindSessionEntity_andSaveSessionWithNewPromptletId(){
        final String sessionId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final SessionEntity sessionEntity = getRandomSessionEntity().id(sessionId).build();
        final String promptletId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));

        when(sessionRepository.findSessionEntityById(sessionId)).thenReturn(sessionEntity);
        when(sessionRepository.save(any())).thenReturn(SessionEntity.aSessionEntityBuilder().build());

        sessionEntity.getPromptlets().add(promptletId);

        mongoSessionDataAccessService.addPromptletToSession(sessionId, promptletId);

        verify(sessionRepository).save(sessionEntity);
    }
}
