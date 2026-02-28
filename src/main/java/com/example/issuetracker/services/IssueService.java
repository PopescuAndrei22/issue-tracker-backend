package com.example.issuetracker.services;

import org.springframework.stereotype.Service;

import com.example.issuetracker.repositories.IssueRepository;
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
}
