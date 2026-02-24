package com.example.issuetracker.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.issuetracker.domain.entities.Project;
import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.domain.models.Role;

public class ProjectTest {

    private static User owner;
    private static Project project;
    
    @BeforeAll
    static void initialSetUp(){
        String email = "Andrew@mycompany.com";
        String username = "andrew123";
        String password = "somepassword";
        Role role = Role.USER;

        owner = new User(username, password, email, role);

        String projectName = "First project";
        String description = "";

        project = new Project(projectName, owner, description);
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

        assertDoesNotThrow(() ->
            new Project("project name", owner, null)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"Internal tool", "Payment microservice"})
    void shouldRenameProjectCorrectly(String testProjectName){

        project.renameProject(testProjectName);

        assertEquals(project.getProjectName(), testProjectName, "The name of the project cannot be changed correctly");
    }

    @Test
    void shouldChangeDescriptionCorrectly(){
        String testDescription = "My description";

        project.changeDescription(testDescription);

        assertEquals(project.getDescription(), testDescription, "The description of the project cannot be changed correctly");
    }
}
