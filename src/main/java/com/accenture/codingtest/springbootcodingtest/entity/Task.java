package com.accenture.codingtest.springbootcodingtest.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank
    @Column(name = "project_id ")
    private UUID project_id;
    @NotBlank
    @Column(name = "user_id ")
    private UUID user_id;
}
