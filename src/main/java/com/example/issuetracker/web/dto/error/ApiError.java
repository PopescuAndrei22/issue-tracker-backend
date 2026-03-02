package com.example.issuetracker.web.dto.error;

public record ApiError(
    int status,
    String error,
    String message
) 
{}
