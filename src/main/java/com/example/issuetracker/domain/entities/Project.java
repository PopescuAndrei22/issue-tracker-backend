package com.example.issuetracker.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name  = "projects")
public class Project extends BaseEntity{

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    protected Project() {}

    public Project(String name, User owner, String description) {

        this.name = validateName(name);
        this.description = validateDescription(description);
        this.owner = validateOwner(owner);
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

    private String validateDescription(String description){
        if(description == null || description.isBlank()){
            return "";
        }
        return description;
    }

    public void transferOwnership(User newOwner){

        if(this.owner.equals(newOwner)){
            return;
        }

        newOwner = validateOwner(newOwner);
        this.owner = newOwner;
    }

    public void renameProject(String name){
        this.name = validateName(name);
    }

    public void changeDescription(String description){
        this.description = description;
    }

    public String getProjectName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }
}
