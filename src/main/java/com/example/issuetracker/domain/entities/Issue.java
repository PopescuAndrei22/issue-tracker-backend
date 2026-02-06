package com.example.issuetracker.domain.entities;

import com.example.issuetracker.domain.models.IssueType;
import com.example.issuetracker.domain.models.Priority;

public class Issue {
    private Long id;

    private String description;
    private String title;

    private IssueType type;
    private Priority priority;

    private User reporter;
    private User assignee;

    private Project project;
    
}
