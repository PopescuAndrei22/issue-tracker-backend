package com.example.issuetracker.web.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.example.issuetracker.domain.entities.Issue;
import com.example.issuetracker.web.dto.IssueResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IssueMapper {
    @Mapping(target = "reporterId", source = "reporter.id")
    @Mapping(target = "assigneeId", source = "assignee.id")
    @Mapping(target = "projectId", source = "project.id")
    IssueResponseDTO toResponseDTO(Issue issue);

    public List<IssueResponseDTO> toResponseDTOList(List<Issue> issues);
}