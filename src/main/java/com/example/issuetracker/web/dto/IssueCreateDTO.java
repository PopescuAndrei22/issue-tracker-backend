package com.example.issuetracker.web.dto;

import com.example.issuetracker.domain.models.IssueType;
import com.example.issuetracker.domain.models.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IssueCreateDTO(
    @NotBlank String title,
    String description,
    @NotNull IssueType type,
    Priority priority,
    @NotNull Long reporterId,
    Long assigneeId,
    @NotNull Long projectId
) 
{}
