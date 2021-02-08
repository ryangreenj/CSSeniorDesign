package com.education.education.promptlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptletService {

    private final PromptletDataAccessService promptletDataAccessService;

    @Autowired
    public PromptletService(@Qualifier("MongoPromptletDataAccessService") final PromptletDataAccessService promptletDataAccessService) {
        this.promptletDataAccessService = promptletDataAccessService;
    }

    public String createPromptlet(final String prompt, final PROMPTLET_TYPE promptlet_type,
                                  final List<String> answerPool, final List<String> correctAnswer){
        return promptletDataAccessService.createPromptlet(prompt, promptlet_type, answerPool, correctAnswer);
    }

    public List<Promptlet> getPromptlets(final List<String> promptletIds){
        return promptletDataAccessService.getPromptlets(promptletIds);
    }
}
