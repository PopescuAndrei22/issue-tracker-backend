package com.example.issuetracker.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.issuetracker.domain.entities.Project;
import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.domain.models.Role;

public class ProjectTest {

    static User owner;
    
    @BeforeAll
    static void initialSetUp(){
        String email = "Andrew@mycompany.com";
        String username = "andrew123";
        String password = "somepassword";
        Role role = Role.USER;

        owner = new User(username, password, email, role);
    }

    @Test
    void shouldThrowErrorWhenProjectNameIsMissing(){
        assertThatThrownBy(() -> 
                new Project("", owner, "project description")
            )
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> 
                new Project(null, owner, "project description")
            )
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowErrorWhenProjectOwnerIsMissing(){
        assertThatThrownBy(() -> 
                new Project("project name", null, "project description")
            )
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotThrowErrorUponInstantiation(){
        assertDoesNotThrow(() ->
            new Project("project name", owner, "project description")
        );
    }
}
