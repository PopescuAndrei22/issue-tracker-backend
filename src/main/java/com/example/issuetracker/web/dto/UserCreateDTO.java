package com.example.issuetracker.web.dto;

import com.example.issuetracker.domain.models.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(

    @NotBlank(message = "The username cannot be blank")
    @Size(min = 3, max = 20, message = "The username must be between 3 and 20 characters")
    String username,

    @NotBlank(message = "The email cannot be blank")
    @Email(message = "Must be a valid email address")
    String email,

    @NotBlank(message = "The password cannot be blank")
    @Size(min = 8, message = "The password must be at least 8 characters long")
    String password,

    @NotNull(message = "The role must be provided")
    Role role
)
{}
