package com.education.education.session.repositories.entities.mappers;

import com.education.education.session.Session;
import com.education.education.session.helpers.RandomSessionEntity;
import com.education.education.session.repositories.entities.SessionEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.education.education.session.Session.aSessionBuilder;
import static com.education.education.session.helpers.RandomSessionEntity.getRandomSessionEntity;
import static com.education.education.session.repositories.entities.mappers.SessionEntityToSessionMapper.mapSessionEntityToSession;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SessionEntityToSessionMapper.class})
class SessionEntityToSessionMapperTest {

    @Test
    void mapSessionEntityToSession_shouldMapSessionEntityToSession() {
        final SessionEntity sessionEntity = getRandomSessionEntity().build();
        final Session expectedSession = aSessionBuilder()
                .id(sessionEntity.getId())
                .sessionName(sessionEntity.getSessionName())
                .promptletIds(sessionEntity.getPromptlets())
                .build();

        assertThat(mapSessionEntityToSession(sessionEntity)).isEqualToComparingFieldByField(expectedSession);
    }


}
