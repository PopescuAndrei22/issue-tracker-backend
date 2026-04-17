package com.example.issuetracker.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.issuetracker.domain.entities.Issue;
import com.example.issuetracker.domain.entities.Project;
import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.repositories.IssueRepository;
import com.example.issuetracker.web.dto.IssueCreateDTO;
import com.example.issuetracker.web.dto.IssueResponseDTO;
import com.example.issuetracker.web.mappers.IssueMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class IssueService {
    private final IssueRepository issueRepository;
    private final UserService userService;
    private final ProjectService projectService;
    private final IssueMapper issueMapper;

    public IssueService(IssueRepository issueRepository, UserService userService, 
                        ProjectService projectService, IssueMapper issueMapper) {
        this.issueRepository = issueRepository;
        this.userService = userService;
        this.projectService = projectService;
        this.issueMapper = issueMapper;
    }

    public IssueResponseDTO createIssue(IssueCreateDTO dto){
        Project project = projectService.getProjectEntityById(dto.projectId());
        User reporter = userService.getUserEntityById(dto.reporterId());

        User assignee = (dto.assigneeId() != null)
                        ? userService.getUserEntityById(dto.assigneeId())
                        : null;

        Issue issue = Issue.builder()
                        .title(dto.title())
                        .description(dto.description())
                        .type(dto.type())
                        .priority(dto.priority())
                        .reporter(reporter)
                        .assignee(assignee)
                        .project(project)
                        .build();

        Issue savedIssue = issueRepository.save(issue);

        return issueMapper.toResponseDTO(savedIssue);
    }

    public Page<IssueResponseDTO> getIssuesAssignedToUser(Long userId, Pageable pageable) {
        Page<Issue> issuePage = issueRepository.findByAssigneeId(userId, pageable);

        return issuePage.map(issueMapper::toResponseDTO);
    }

    public Page<IssueResponseDTO> getIssuesAssignedToReporter(Long reporterId, Pageable pageable) {
        Page<Issue> issuePage = issueRepository.findByReporterId(reporterId, pageable);

        return issuePage.map(issueMapper::toResponseDTO);
    }

    public Page<IssueResponseDTO> getIssuesAssignedToProject(Long projectId, Pageable pageable) {
        Page<Issue> issuePage = issueRepository.findByProjectId(projectId, pageable);

        return issuePage.map(issueMapper::toResponseDTO);
    }

    public IssueResponseDTO getIssueById(Long id){
        return issueRepository.findById(id)
                              .map(issueMapper::toResponseDTO)
                              .orElseThrow(() -> new RuntimeException("The issue with id " + id + " not found."));
    }
}
