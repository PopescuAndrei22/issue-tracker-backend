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
    private final List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy="assignee")
    private final List<Issue> assignedIssues = new ArrayList<>();

    @OneToMany(mappedBy="reporter")
    private final List<Issue> reportedIssues = new ArrayList<>();

    void addProject(Project project){
        if(!this.projects.contains(project)){
            this.projects.add(project);
        }
    }

    void removeProject(Project project){
        this.projects.remove(project);
    }

    void addAssignedIssue(Issue issue){
        if(!this.assignedIssues.contains(issue)){
            this.assignedIssues.add(issue);
        }
    }

    void removeAssignedIssue(Issue issue){
        this.assignedIssues.remove(issue);
    }

    void addReportedIssue(Issue issue){
        if(!this.reportedIssues.contains(issue)){
            this.reportedIssues.add(issue);
        }
    }

    protected User() {}

    public User(String username, String password, String email, Role role) {

        this.changeUsername(username);
        this.changePassword(password);
        this.changeEmail(email);
        this.changeRole(role);
    }

    private void changeUsername(String username){
        if(username == null || username.isBlank()){
            throw new IllegalArgumentException("The username cannot be blank");
        }
        this.username = username;
    }

    public void changeEmail(String email){
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("The email cannot be blank");
        }
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public void changePassword(String password){
        if(password == null || password.isBlank()){
            throw new IllegalArgumentException("The password cannot be blank");
        }
        this.password = password;
    }

    public void changeRole(Role role){
        if(role == null){
            throw new IllegalArgumentException("The role must be provided");
        }
        this.role = role;
    }

    public Role getRole(){
        return this.role;
    }

    public List<Project> getProjects(){
        return List.copyOf(this.projects);
    }

    public List<Issue> getAssignedIssues(){
        return List.copyOf(this.assignedIssues);
    }

    public List<Issue> getReportedIssues(){
        return List.copyOf(this.reportedIssues);
    }
}
