package com.education.education.session;

import com.education.education.session.repositories.entities.SessionEntity;

import java.util.List;

public interface SessionDataAccessService {

    String insertSession(final String sessionName);

    List<SessionEntity> getSessions(final List<String> sessions);
}
