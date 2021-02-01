package com.education.education.session;

import com.education.education.session.repositories.entities.mappers.SessionEntityToSessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class SessionService {

    private final SessionDataAccessService sessionDataAccessService;

    @Autowired
    public SessionService(@Qualifier("MongoSessionDataAccessService") final SessionDataAccessService sessionDataAccessService) {
        this.sessionDataAccessService = sessionDataAccessService;
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
}
