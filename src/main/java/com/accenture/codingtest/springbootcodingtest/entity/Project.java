package com.accenture.codingtest.springbootcodingtest.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "Project")
public class Project {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "created_by")
    private String created_by;

    public Project() {
    }

    public Project(String name, String created_by) {
        this.name = name;
        this.created_by = created_by;
    }

    public Project(UUID id, String name, String created_by) {
        this.id = id;
        this.name = name;
        this.created_by = created_by;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
}