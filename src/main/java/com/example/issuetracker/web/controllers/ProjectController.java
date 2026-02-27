package com.example.issuetracker.web.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.issuetracker.services.ProjectService;
import com.example.issuetracker.web.dto.ProjectCreateDTO;
import com.example.issuetracker.web.dto.ProjectResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectCreateDTO dto){

        ProjectResponseDTO createdProject = projectService.createProject(dto);

        URI location = URI.create("/projects/" + createdProject.id());

        return ResponseEntity
                .created(location)
                .body(createdProject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id){
        return ResponseEntity.ok(projectService.getProjectById(id));
    }
}
