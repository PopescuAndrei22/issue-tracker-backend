package com.example.issuetracker.domain.entities;

import com.example.issuetracker.domain.models.Role;

public class User {
    private Long id;

    private String username;
    private String email;
    private String password;

    private Role role;
}
