package com.example.issuetracker.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import com.example.issuetracker.domain.entities.Issue;
import com.example.issuetracker.domain.entities.Project;
import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.domain.models.IssueType;
import com.example.issuetracker.domain.models.Priority;

public class IssueTest {

    @Test
    public void shouldThrowErrorWhenTitleIsMissing(){

        User mockReporter = mock(User.class);
        Project mockProject = mock(Project.class);

        assertThatThrownBy(() -> 
                Issue.builder()
                    .reporter(mockReporter)
                    .project(mockProject)
                    .priority(Priority.MEDIUM)
                    .type(IssueType.BUG)
                    .build())
            .isInstanceOf(IllegalArgumentException.class);
    }
}
