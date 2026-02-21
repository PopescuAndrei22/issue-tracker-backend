package com.example.issuetracker.web.dto;

import com.example.issuetracker.domain.models.Role;

public record UserResponseDTO(
    Long id,
    String username,
    String email,
    Role role
) {}