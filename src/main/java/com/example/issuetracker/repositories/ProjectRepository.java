package com.example.issuetracker.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.issuetracker.domain.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{
    
    @EntityGraph(attributePaths = {"owner"})
    Page<Project> findByOwnerId(Long ownerId, Pageable pageable);
}
