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
    private final List<Issue> issues = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    void addIssue(Issue issue){
        this.issues.add(issue);
    }

    void removeIssue(Issue issue){
        this.issues.remove(issue);
    }

    protected Project() {}

    public Project(String name, User owner, String description) {

        this.name = validateName(name);
        this.description = description;
        this.assignToOwner(owner);
    }

    private String validateName(String name){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Project name must be provided");
        }
        return name;
    }

    private User validateOwner(User owner){
        if(owner == null){
            throw new IllegalArgumentException("The project must have an owner assigned");
        }
        return owner;
    }

    private void assignToOwner(User owner){
        owner = validateOwner(owner);

        this.owner = owner;
        this.owner.addProject(this);
    }

    public void transferOwnership(User newOwner){

        if(this.owner.equals(newOwner)){
            return;
        }

        newOwner = validateOwner(newOwner);

        if(this.owner != null){
            this.owner.removeProject(this);
        }

        this.assignToOwner(newOwner);
    }

    public void renameProject(String name){
        this.name = validateName(name);
    }

    public void changeDescription(String description){
        this.description = description;
    }

    public boolean hasOpenIssues(){
        return !this.issues.isEmpty();
    }

    public List<Issue> getIssues(){
        return List.copyOf(this.issues);
    }
}
