package com.example.issuetracker.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{
    private String resource;
    private Long id;

    public ResourceNotFoundException(String resource, Long id){
        super(resource + "with ID: " + id + " not found.");
        this.id = id;
        this.resource = resource;
    }
}
