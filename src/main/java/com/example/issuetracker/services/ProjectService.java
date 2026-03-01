package com.example.issuetracker.services;

import org.springframework.stereotype.Service;

import com.example.issuetracker.domain.entities.Project;
import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.repositories.ProjectRepository;
import com.example.issuetracker.web.dto.ProjectCreateDTO;
import com.example.issuetracker.web.dto.ProjectResponseDTO;
import com.example.issuetracker.web.mappers.ProjectMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectMapper projectMapper;

    public ProjectService
    (
        ProjectRepository projectRepository, 
        UserService userService, 
        ProjectMapper projectMapper
    )
    {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.projectMapper = projectMapper;
    }

    public ProjectResponseDTO createProject(ProjectCreateDTO dto){

        User owner = userService.getUserEntityById(dto.ownerId());

        Project project = new Project(
            dto.name(),
            owner,
            dto.description()
        );

        Project savedProject = projectRepository.save(project);

        return projectMapper.toResponseDTO(savedProject);
    }

    public ProjectResponseDTO getProjectById(Long id){
        return projectRepository.findById(id)
                                .map(projectMapper::toResponseDTO)
                                .orElseThrow(() -> new RuntimeException("Project with id " + id + " not found."));
    }

    Project getProjectEntityById(Long id){
        return projectRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Project with id " + id + " not found."));
    }
}
