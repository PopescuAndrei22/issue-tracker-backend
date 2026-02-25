package com.example.issuetracker.web.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.example.issuetracker.domain.entities.Project;
import com.example.issuetracker.web.dto.ProjectResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectMapper {
    
    @Mapping(target = "ownerId", source = "owner.id")
    public ProjectResponseDTO toResponseDTO(Project project);

    public List<ProjectResponseDTO> toResponseDTOList(List<Project> projects);
}
