package com.education.education.session.repositories.entities.mappers;

import com.education.education.session.Session;
import com.education.education.session.repositories.entities.SessionEntity;

import static com.education.education.session.Session.aSessionBuilder;

public class SessionEntityToSessionMapper {

    public static Session mapSessionEntityToSession(final SessionEntity sessionEntity){
        return aSessionBuilder()
                .id(sessionEntity.getId())
                .sessionName(sessionEntity.getSessionName())
                .promptletIds(sessionEntity.getPromptlets())
                .isArchived(sessionEntity.isArchived())
                .build();
    }
}
