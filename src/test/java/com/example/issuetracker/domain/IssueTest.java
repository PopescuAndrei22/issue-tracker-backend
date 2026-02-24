package com.example.issuetracker.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import com.example.issuetracker.domain.entities.Issue;
import com.example.issuetracker.domain.entities.Project;
import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.domain.models.IssueType;
import com.example.issuetracker.domain.models.Role;

class IssueTest {

    private static User reporter;
    private static Project project;

    @BeforeAll
    static void initialSetUp(){
        String username = "username123";
        String password = "justapassword";
        String email = "username23@company.com";
        Role role = Role.USER;

        reporter = new User(username, password, email, role);

        String projectName = "Internal tool for HR";
        String projectDescription = "Sample description";

        project = new Project(projectName, reporter, projectDescription);
    }

    @Test
    void shouldThrowErrorWhenTitleIsMissing(){

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
    void shouldThrowErrorWhenReporterIsMissing(){

        assertThatThrownBy(() -> 
                Issue.builder()
                    .title("Fix bug login")
                    .project(project)
                    .type(IssueType.BUG)
                    .build())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowErrorWhenProjectIsMissing(){

        assertThatThrownBy(() -> 
                Issue.builder()
                    .title("Fix bug login")
                    .reporter(reporter)
                    .type(IssueType.BUG)
                    .build())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowErrorWhenIssueTypeIsMissing(){

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
