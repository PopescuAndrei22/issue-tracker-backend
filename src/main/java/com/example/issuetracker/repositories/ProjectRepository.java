package com.example.issuetracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.issuetracker.domain.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
