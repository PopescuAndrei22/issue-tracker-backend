package com.example.issuetracker.web.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.issuetracker.services.IssueService;
import com.example.issuetracker.web.dto.IssueCreateDTO;
import com.example.issuetracker.web.dto.IssueResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService){
        this.issueService = issueService;
    }

    @PostMapping
    public ResponseEntity<IssueResponseDTO> createIssue(@Valid @RequestBody IssueCreateDTO dto){
        IssueResponseDTO createdIssue = issueService.createIssue(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdIssue.id())
                .toUri();
        
        return ResponseEntity.created(location).body(createdIssue);
    }

    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<Page<IssueResponseDTO>> getAssigneeIssues(
            @PathVariable Long assigneeId,
            @PageableDefault(
                page = 0,
                size = 10,
                sort = "createdAt",
                direction = Sort.Direction.DESC
            )
            Pageable pageable) {
        
        return ResponseEntity.ok(issueService.getIssuesAssignedToUser(assigneeId, pageable));
    }

    @GetMapping("/reporter/{reporterId}")
    public ResponseEntity<Page<IssueResponseDTO>> getReporterIssues(
            @PathVariable Long reporterId,
            @PageableDefault(
                page = 0,
                size = 10,
                sort = "createdAt",
                direction = Sort.Direction.DESC
            )
            Pageable pageable) {
        
        return ResponseEntity.ok(issueService.getIssuesAssignedToReporter(reporterId, pageable));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Page<IssueResponseDTO>> getProjectIssues(
            @PathVariable Long projectId,
            @PageableDefault(
                page = 0,
                size = 10,
                sort = "createdAt",
                direction = Sort.Direction.DESC
            )
            Pageable pageable) {
        
        return ResponseEntity.ok(issueService.getIssuesAssignedToProject(projectId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueResponseDTO> getIssueById(@PathVariable Long id){
        return ResponseEntity.ok(issueService.getIssueById(id));
    }

}
