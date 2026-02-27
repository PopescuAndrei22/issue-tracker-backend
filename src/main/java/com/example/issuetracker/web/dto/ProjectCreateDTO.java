package com.example.issuetracker.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectCreateDTO(

    @NotBlank(message = "The name of the project cannot be blank")
    @Size(min = 3, max = 20, message = "The project name must be between 3 and 20 characters")
    String name,

    @NotNull(message = "The owner ID cannot be blank")
    Long ownerId,

    String description
)
{}
