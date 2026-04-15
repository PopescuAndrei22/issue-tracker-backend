DROP TABLE IF EXISTS issues;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    owner_id BIGINT NOT NULL,
    CONSTRAINT fk_project_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE issues (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    type VARCHAR(20) NOT NULL,   
    priority VARCHAR(20) NOT NULL, 
    status VARCHAR(20) NOT NULL,  
    reporter_id BIGINT NOT NULL,
    assignee_id BIGINT,
    project_id BIGINT NOT NULL,
    CONSTRAINT fk_issue_reporter FOREIGN KEY (reporter_id) REFERENCES users(id),
    CONSTRAINT fk_issue_assignee FOREIGN KEY (assignee_id) REFERENCES users(id),
    CONSTRAINT fk_issue_project  FOREIGN KEY (project_id) REFERENCES projects(id)
);