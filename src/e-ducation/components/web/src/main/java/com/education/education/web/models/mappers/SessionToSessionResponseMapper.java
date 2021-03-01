package com.education.education.web.models.mappers;

import com.education.education.session.Session;
import com.education.education.web.models.SessionResponse;

import static com.education.education.web.models.SessionResponse.aSessionResponseBuilder;

public class SessionToSessionResponseMapper {

    public static SessionResponse mapSessionToSessionResponse(final Session session){
        return aSessionResponseBuilder()
                .sessionName(session.getSessionName())
                .id(session.getId())
                .promptletIds(session.getPromptletIds())
                .isArchived(session.isArchived())
                .build();
    }
}
