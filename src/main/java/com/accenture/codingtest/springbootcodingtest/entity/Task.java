package com.accenture.codingtest.springbootcodingtest.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @NotBlank
    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "project_id")
    private UUID project_id;
    @NotNull
    @Column(name = "user_id")
    private UUID user_id;

    public Task() {
    }

    public Task(UUID id, String title, String description, String status, UUID project_id, UUID user_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.project_id = project_id;
        this.user_id = user_id;
    }

    public Task(String title, String description, String status, UUID project_id, UUID user_id) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.project_id = project_id;
        this.user_id = user_id;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public UUID getProject_id() {
        return project_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProject_id(UUID project_id) {
        this.project_id = project_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }
}