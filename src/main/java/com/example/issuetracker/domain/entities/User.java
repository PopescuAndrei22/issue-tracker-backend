package com.example.issuetracker.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.example.issuetracker.domain.models.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name  = "users")
public class User extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy="owner")
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy="assignee")
    private List<Issue> assignedIssues = new ArrayList<>();

    @OneToMany(mappedBy="reporter")
    private List<Issue> reportedIssues = new ArrayList<>();

    protected User() {}

    public User(String username, String password, String email, Role role) {

        if(username == null || username.isBlank()){
            throw new IllegalArgumentException("The username cannot be blank");
        }
        if(password == null || password.isBlank()){
            throw new IllegalArgumentException("The password cannot be blank");
        }
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("The email cannot be blank");
        }
        if(role == null){
            throw new IllegalArgumentException("The role must be provided");
        }

        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
