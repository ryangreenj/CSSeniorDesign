package com.education.education.web.models.mappers;

import com.education.education.session.Session;
import com.education.education.web.models.SessionResponse;
import org.junit.jupiter.api.Test;

import static com.education.education.web.helpers.RandomSession.randomSessionBuilder;
import static com.education.education.web.models.SessionResponse.aSessionResponseBuilder;
import static com.education.education.web.models.mappers.SessionToSessionResponseMapper.mapSessionToSessionResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SessionToSessionResponseMapperTest {

    @Test
    void mapSessionToSessionResponse_shouldMapSessionToSessionResponse() {
        final Session session = randomSessionBuilder().build();
        final SessionResponse expectedSessionResponse = aSessionResponseBuilder()
                .id(session.getId())
                .sessionName(session.getSessionName())
                .promptletIds(session.getPromptletIds())
                .build();

        assertThat(mapSessionToSessionResponse(session)).isEqualToComparingFieldByField(expectedSessionResponse);
    }


}
