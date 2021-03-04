package com.education.education.promptlet.repositories.entities;

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
    private List<String> userResponses;
    private boolean isVisible;

    public void setUserResponses(List<String> userResponses) {
        this.userResponses = userResponses;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public static PromptletEntityBuilder aPromptletEntityBuilder() {
        return PromptletEntity.builder();
    }
}
