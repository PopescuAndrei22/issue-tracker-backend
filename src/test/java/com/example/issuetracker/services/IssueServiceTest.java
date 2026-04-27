package com.example.issuetracker.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.verifyNoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.issuetracker.domain.entities.Issue;
import com.example.issuetracker.domain.entities.Project;
import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.domain.models.IssueType;
import com.example.issuetracker.domain.models.Priority;
import com.example.issuetracker.domain.models.Role;
import com.example.issuetracker.exceptions.ResourceNotFoundException;
import com.example.issuetracker.repositories.IssueRepository;
import com.example.issuetracker.web.dto.IssueCreateDTO;
import com.example.issuetracker.web.mappers.IssueMapper;

@ExtendWith(MockitoExtension.class)
class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProjectService projectService;

    @Mock
    private IssueMapper issueMapper;

    @InjectMocks
    private IssueService issueService;

    private User reporter;
    private Project project;

    @BeforeEach
    void setup(){
        String username = "Andrew22";
        String password = "simplepassword";
        String email = "andrew@email.com";
        Role role = Role.ADMIN;
        reporter = new User(username, password, email, role);

        String name = "My first project";
        String description = "It is about issue tracking";
        project = new Project(name, reporter, description);
    }

    @Test
    void shouldCreateTheIssueSuccessfully(){
        String title = "mytitle1";
        String description = "none";
        IssueType type = IssueType.ENHANCEMENT;
        Priority priority = null;
        Long reporterId = 5L;
        Long assigneeId = null;
        Long projectId = 3L;
        IssueCreateDTO dto = new IssueCreateDTO(
                title,
                description,
                type,
                priority,
                reporterId,
                assigneeId,
                projectId
        );
        when(userService.getUserEntityById(reporterId)).thenReturn(reporter);
        when(projectService.getProjectEntityById(projectId)).thenReturn(project);
        when(issueRepository.save(any(Issue.class))).thenAnswer(invocation -> invocation.getArgument(0));

        issueService.createIssue(dto);

        verify(userService).getUserEntityById(reporterId);
        verify(projectService).getProjectEntityById(projectId);
        ArgumentCaptor<Issue> issueCaptor = ArgumentCaptor.forClass(Issue.class);
        verify(issueRepository).save(issueCaptor.capture());
        Issue issue = issueCaptor.getValue();
        assertEquals(issue.getReporter(), reporter);
        assertEquals(issue.getProject(), project);
        assertEquals(issue.getType(), type);
        verify(issueMapper).toResponseDTO(any(Issue.class));
    }

    @Test
    void shouldThrowWhenIssueDoesNotExist(){
        Long issueId = 99L;
        when(issueRepository.findById(issueId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> 
                issueService.getIssueById(issueId))
            .isInstanceOf(ResourceNotFoundException.class);

        verifyNoInteractions(issueMapper);
    }
}
