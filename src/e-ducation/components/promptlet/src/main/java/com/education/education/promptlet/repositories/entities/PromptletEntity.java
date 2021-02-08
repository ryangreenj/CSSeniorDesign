package com.education.education.promptlet.repositories.entities;

import com.education.education.promptlet.PROMPTLET_TYPE;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Builder
@Getter
@Document(collection = "Promptlets")
public class PromptletEntity {

    @Id
    private final String id;
    private final String prompt;
    private final String promptletType;
    private final List<String> responsePool;
    private final List<String> correctAnswer;
    private final List<String> userResponses;

    public static PromptletEntityBuilder aPromptletEntityBuilder() {
        return PromptletEntity.builder();
    }
}
