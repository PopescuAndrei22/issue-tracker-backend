package com.example.issuetracker.domain.entities;

import java.time.Instant;

public abstract class BaseEntity {

    protected Long id;

    protected Instant createdAt;

    protected Instant updatedAt;
}
