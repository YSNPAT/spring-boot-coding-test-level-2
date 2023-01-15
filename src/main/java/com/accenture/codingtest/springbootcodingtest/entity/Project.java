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

}
