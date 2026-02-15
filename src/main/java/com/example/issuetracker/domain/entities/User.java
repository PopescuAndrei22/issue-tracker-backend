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
        this.projects.add(project);
    }

    void removeProject(Project project){
        this.projects.remove(project);
    }

    void addAssignedIssue(Issue issue){
        this.assignedIssues.add(issue);
    }

    void removeAssignedIssue(Issue issue){
        this.assignedIssues.remove(issue);
    }

    void addReportedIssue(Issue issue){
        this.reportedIssues.add(issue);
    }

    protected User() {}

    public User(String username, String password, String email, Role role) {

        this.username = validateUsername(username);
        this.password = validatePassword(password);
        this.email = validateEmail(email);
        this.role = validateRole(role);
    }

    private String validateUsername(String username){
        if(username == null || username.isBlank()){
            throw new IllegalArgumentException("The username cannot be blank");
        }

        return username;
    }

    private String validateEmail(String email){
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("The email cannot be blank");
        }

        return email;
    }

    private String validatePassword(String password){
        if(password == null || password.isBlank()){
            throw new IllegalArgumentException("The password cannot be blank");
        }
        return password;
    }

    private Role validateRole(Role role){
        if(role == null){
            throw new IllegalArgumentException("The role must be provided");
        }
        return role;
    }

    public void changeUsername(String username){
        this.username = validateUsername(username);
    }

    public void changeEmail(String email){
        this.email = validateEmail(email);
    }

    public void changePassword(String password){
        this.password = validatePassword(password);
    }

    public void changeRole(Role role){
        this.role = validateRole(role);
    }

    public boolean matchesPassword(String password){
        return this.password.equals(validatePassword(password));
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
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
