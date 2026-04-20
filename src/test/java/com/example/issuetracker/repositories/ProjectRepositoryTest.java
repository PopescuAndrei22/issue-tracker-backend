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

import com.example.issuetracker.domain.entities.Project;
import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.domain.models.Role;

@DataJpaTest
@ActiveProfiles("test")
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User testOwner;

    @BeforeEach
    void setUp() {
        testOwner = new User("alice", "pass", "alice@test.com", Role.USER);
        entityManager.persist(testOwner);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void shouldReturnCorrectProjectsBasedOnOwnerId() {
        entityManager.persist(new Project("Project A", testOwner, "Desc A"));
        entityManager.persist(new Project("Project B", testOwner, "Desc B"));
        entityManager.persist(new Project("Project C", testOwner, "Desc C"));
        entityManager.flush();
        entityManager.clear();

        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<Project> result = projectRepository.findByOwnerId(testOwner.getId(), pageRequest);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getContent().get(0).getOwner().getUsername()).isEqualTo("alice");
        assertThat(result.getContent().get(0).getName()).isEqualTo("Project A");
        assertThat(result.getContent().get(1).getName()).isEqualTo("Project B");
    }

}
