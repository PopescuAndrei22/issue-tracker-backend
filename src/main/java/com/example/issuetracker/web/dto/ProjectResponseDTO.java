package com.example.issuetracker.web.dto;

public record ProjectResponseDTO(
    Long id,
    String name,
    Long ownerId,
    String description
)
{}