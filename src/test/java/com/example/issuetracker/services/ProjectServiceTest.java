package com.example.issuetracker.services;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.issuetracker.domain.entities.Project;
import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.domain.models.Role;
import com.example.issuetracker.exceptions.ResourceNotFoundException;
import com.example.issuetracker.repositories.ProjectRepository;
import com.example.issuetracker.web.dto.ProjectCreateDTO;
import com.example.issuetracker.web.mappers.ProjectMapper;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectService projectService;

    private User owner;

    @BeforeEach
    void setupOwner(){
        String username = "Andrew22";
        String password = "simplepassword";
        String email = "andrew@email.com";
        Role role = Role.ADMIN;
        owner = new User(username, password, email, role);
    }

    @Test
    void shouldCreateTheProjectSuccessfullyWithExistingOwner(){
        String projectName = "firstproject";
        Long ownerId = 1L;
        String description = "It does a lot of things";
        ProjectCreateDTO dto = new ProjectCreateDTO(
            projectName,
            ownerId,
            description
        );
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userService.getUserEntityById(ownerId)).thenReturn(owner);

        projectService.createProject(dto);

        verify(userService).getUserEntityById(ownerId);
        ArgumentCaptor<Project> projectCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(projectCaptor.capture());
        Project project = projectCaptor.getValue();
        assertEquals(project.getOwner(), owner);
        assertEquals(project.getName(), projectName);
        assertEquals(project.getDescription(), description);
        verify(projectMapper).toResponseDTO(any(Project.class));
    }

    @Test
    void shouldThrowWhenProjectDoesNotExist(){
        Long projectId = 99L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> 
                projectService.getProjectById(projectId))
            .isInstanceOf(ResourceNotFoundException.class);

        assertThatThrownBy(() -> 
                projectService.getProjectEntityById(projectId))
            .isInstanceOf(ResourceNotFoundException.class);

        verifyNoInteractions(projectMapper);
    }
}
