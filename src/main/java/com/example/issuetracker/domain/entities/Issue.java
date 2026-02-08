package com.example.issuetracker.domain.entities;

import com.example.issuetracker.domain.models.IssueType;
import com.example.issuetracker.domain.models.Priority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name  = "issues")
public class Issue extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 200)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    protected Issue() {}

    private Issue(IssueBuilder issueBuilder){
        this.title = issueBuilder.title;
        this.description = issueBuilder.description;
        this.type = issueBuilder.type;
        this.priority = issueBuilder.priority;
        this.reporter = issueBuilder.reporter;
        this.assignee = issueBuilder.assignee;
        this.project = issueBuilder.project;
    }

    public static IssueBuilder builder(){
        return new IssueBuilder();
    }

    public static class IssueBuilder{

        private String description;
        private String title;
        private IssueType type;
        private Priority priority;
        private User reporter;
        private User assignee;
        private Project project;

        private IssueBuilder() {}

        public IssueBuilder description(String description){
            this.description = description;
            return this;
        }

        public IssueBuilder title(String title){
            this.title = title;
            return this;
        }

        public IssueBuilder type(IssueType type){
            this.type = type;
            return this;
        }
        public IssueBuilder priority(Priority priority){
            this.priority = priority;
            return this;
        }
        public IssueBuilder reporter(User reporter){
            this.reporter = reporter;
            return this;
        }
        public IssueBuilder assignee(User assignee){
            this.assignee = assignee;
            return this;
        }
        public IssueBuilder project(Project project){
            this.project = project;
            return this;
        }
        public Issue build(){
            validate();
            return new Issue(this);
        }

        public void validate(){
            if(this.title == null || this.title.isBlank()){
                throw new IllegalArgumentException("The title cannot be empty");
            }
            if(this.type == null){
                throw new IllegalArgumentException("The issue type must be provided");
            }
            if(this.priority == null){
                throw new IllegalArgumentException("The priority must be provided");
            }
            if(this.reporter == null){
                throw new IllegalArgumentException("The reporter must be provided");
            }
            if(this.project == null){
                throw new IllegalArgumentException("The project must be provided");
            }
        }
    }
    
}
