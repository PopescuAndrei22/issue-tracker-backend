package com.example.issuetracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.issuetracker.domain.entities.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long>{

}
