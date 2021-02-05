package com.education.education.session;

import com.education.education.session.repositories.SessionRepository;
import com.education.education.session.repositories.entities.SessionEntity;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.education.education.session.repositories.entities.SessionEntity.aSessionEntityBuilder;

@Repository("MongoSessionDataAccessService")
public class MongoSessionDataAccessService implements SessionDataAccessService{

    private final SessionRepository sessionRepository;

    @Autowired
    public MongoSessionDataAccessService(final SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public String insertSession(final String sessionName) {
        try{
            return sessionRepository.save(
                    aSessionEntityBuilder()
                            .sessionName(sessionName)
                            .promptlets(new ArrayList<>())
                            .build()).getId();
        } catch (MongoException mongoException){
            throw new RuntimeException("TEMp");
        }
    }

    @Override
    public List<SessionEntity> getSessions(List<String> sessions) {
        try{
            return sessions.stream().map(sessionRepository::findSessionEntityById).collect(Collectors.toList());
        } catch (MongoException mongoException){
            throw new RuntimeException("TEMp");
        }
    }
}
