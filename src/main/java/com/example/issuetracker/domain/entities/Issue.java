package com.example.issuetracker.domain.entities;

import com.example.issuetracker.domain.models.IssueType;
import com.example.issuetracker.domain.models.Priority;
import com.example.issuetracker.domain.models.Status;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.IN_PROGRESS;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public void changeStatus(Status newStatus){
        this.status = newStatus;
    }

    public void changePriority(Priority newPriority){
        this.priority = newPriority;
    }

    public void changeType(IssueType type){
        this.type = type;
    }

    public void updateDescription(String description){
        this.description = description;
    }

    public void updateTitle(String title){
        this.title = title;
    }

    private String validateTitle(String title){
        if(title == null || title.isBlank()){
            throw new IllegalArgumentException("The title cannot be empty");
        }
        return title;
    }

    private IssueType validateType(IssueType type){
        if(type == null){
            throw new IllegalArgumentException("The issue type must be provided");
        }
        return type;
    }

    private Priority validatePriority(Priority priority){
        if(priority == null){
            throw new IllegalArgumentException("The priority type must be provided");
        }
        return priority;
    }

    private String validateDescription(String description){
        if(description == null || description.isBlank()){
            return "";
        }
        return description;
    }

    private User validateUser(User user){
        if(user == null){
            throw new IllegalArgumentException("The user cannot be null");
        }
        return user;
    }

    private Project validateProject(Project project){
        if(project == null){
            throw new IllegalArgumentException("The project cannot be null");
        }
        return project;
    }

    protected Issue() {}

    private Issue(IssueBuilder issueBuilder){
        this.title = validateTitle(issueBuilder.title);
        this.description = validateDescription(issueBuilder.description);
        this.type = validateType(issueBuilder.type);
        this.priority = validatePriority(issueBuilder.priority);
        this.reporter = validateUser(issueBuilder.reporter);
        this.assignee = issueBuilder.assignee;
        this.project = validateProject(issueBuilder.project);
    }

    public static IssueBuilder builder(){
        return new IssueBuilder();
    }

    public static class IssueBuilder{

        private String description;
        private String title;
        private IssueType type;
        private Priority priority = Priority.MEDIUM;
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
            return new Issue(this);
        }
    }
}
