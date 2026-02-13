package com.example.issuetracker.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import com.example.issuetracker.domain.entities.Issue;
import com.example.issuetracker.domain.entities.Project;
import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.domain.models.IssueType;
import com.example.issuetracker.domain.models.Priority;
import com.example.issuetracker.domain.models.Role;

public class IssueTest {

    static User reporter;
    static Project project;

    @BeforeAll
    static void initialSetUp(){
        reporter = mock(User.class);
        project = mock(Project.class);
    }

    @Test
    public void shouldThrowErrorWhenTitleIsMissing(){

        assertThatThrownBy(() -> 
                Issue.builder()
                    .reporter(reporter)
                    .project(project)
                    .type(IssueType.BUG)
                    .build())
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> 
                Issue.builder()
                    .title("")
                    .reporter(reporter)
                    .project(project)
                    .type(IssueType.BUG)
                    .build())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowErrorWhenReporterIsMissing(){

        assertThatThrownBy(() -> 
                Issue.builder()
                    .title("Fix bug login")
                    .project(project)
                    .type(IssueType.BUG)
                    .build())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowErrorWhenProjectIsMissing(){

        assertThatThrownBy(() -> 
                Issue.builder()
                    .title("Fix bug login")
                    .reporter(reporter)
                    .type(IssueType.BUG)
                    .build())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowErrorWhenIssueTypeIsMissing(){

        assertThatThrownBy(() -> 
                Issue.builder()
                    .title("Fix bug login")
                    .reporter(reporter)
                    .project(project)
                    .build())
            .isInstanceOf(IllegalArgumentException.class);
    }

   @Test
    void shouldNotThrowErrorUponInstantiation(){
        assertDoesNotThrow(() ->
            Issue.builder()
                .title("Fix bug login")
                .reporter(reporter)
                .project(project)
                .type(IssueType.BUG)
                .build()
        );
    }
}
