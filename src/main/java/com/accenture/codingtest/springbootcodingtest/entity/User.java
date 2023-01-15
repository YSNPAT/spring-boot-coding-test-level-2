package com.accenture.codingtest.springbootcodingtest.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;
}
