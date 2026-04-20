package com.example.issuetracker.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.example.issuetracker.domain.entities.Issue;
import com.example.issuetracker.domain.entities.Project;
import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.domain.models.IssueType;
import com.example.issuetracker.domain.models.Priority;
import com.example.issuetracker.domain.models.Role;

@DataJpaTest
@ActiveProfiles("test")
public class IssueRepositoryTest {
    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User worker;
    private Project project;

    @BeforeEach
    void setUp() {
        User owner = new User("admin", "pass", "admin@test.com", Role.ADMIN);
        worker = new User("bob_dev", "pass", "bob@test.com", Role.USER);
        
        entityManager.persist(owner);
        entityManager.persist(worker);

        project = new Project("Core API", owner, "Main project");
        entityManager.persist(project);
        
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void shouldFindIssuesByAssigneeWithPaginationAndLoadedRelations() {
        for (int i = 1; i <= 3; i++) {
            Issue issue = Issue.builder()
                    .title("Task " + i)
                    .type(IssueType.BUG)
                    .priority(Priority.MEDIUM)
                    .project(project)
                    .reporter(worker)
                    .assignee(worker)
                    .build();
            entityManager.persist(issue);
        }
        entityManager.flush();
        entityManager.clear();

        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<Issue> result = issueRepository.findByAssigneeId(worker.getId(), pageRequest);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getContent().get(0).getProject().getName()).isEqualTo("Core API");
        assertThat(result.getContent().get(0).getAssignee().getUsername()).isEqualTo("bob_dev");
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Task 1");
    }

}
