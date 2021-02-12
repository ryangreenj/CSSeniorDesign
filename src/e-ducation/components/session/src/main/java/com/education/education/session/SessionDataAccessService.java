package com.education.education.session;

import com.education.education.session.repositories.entities.SessionEntity;

import java.util.List;

public interface SessionDataAccessService {

    String insertSession(final String sessionName);

    void addPromptletToSession(final String sessionId, final String promptletId);

    List<SessionEntity> getSessions(final List<String> sessions);
}
