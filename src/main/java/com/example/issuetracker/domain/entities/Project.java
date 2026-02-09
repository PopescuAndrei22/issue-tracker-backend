package com.example.issuetracker.domain.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name  = "projects")
public class Project extends BaseEntity{

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    @OneToMany(mappedBy="project")
    private List<Issue> issues = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    void addIssue(Issue issue){
        if(!this.issues.contains(issue)){
            this.issues.add(issue);
        }
    }

    private void assignToOwner(User owner){
        this.owner = owner;
        owner.addProject(this);
    }

    protected Project() {}

    public Project(String name, User owner, String description) {

        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Project name must be provided");
        }
        if(owner == null){
            throw new IllegalArgumentException("The project must have an owner assigned");
        }

        this.name = name;
        this.description = description;
        this.assignToOwner(owner);
    }

}
