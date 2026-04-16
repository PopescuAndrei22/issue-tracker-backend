package com.example.issuetracker.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.issuetracker.domain.entities.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long>{

    @EntityGraph(attributePaths = {"project", "reporter", "assignee"})
    Page<Issue> findByAssigneeId(Long assigneeId, Pageable pageable);

    @EntityGraph(attributePaths = {"project", "reporter", "assignee"})
    Page<Issue> findByReporterId(Long reporterId, Pageable pageable);

    @EntityGraph(attributePaths = {"project", "reporter", "assignee"})
    Page<Issue> findByProjectId(Long projectId, Pageable pageable);
}
