package com.example.issuetracker.web.dto;

import com.example.issuetracker.domain.models.IssueType;
import com.example.issuetracker.domain.models.Priority;
import com.example.issuetracker.domain.models.Status;

public record IssueResponseDTO(
Long id,
    String title,
    String description,
    IssueType type,
    Priority priority,
    Status status,
    Long reporterId,
    Long assigneeId,
    Long projectId
) 
{}
