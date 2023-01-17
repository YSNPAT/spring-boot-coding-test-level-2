package com.accenture.codingtest.springbootcodingtest.repository;

import com.accenture.codingtest.springbootcodingtest.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query("SELECT p.id FROM Project p WHERE p.name = :name")
    Optional<Project> findProjectByName (String name);

    Page<Project> findAllByNameContaining(String name, Pageable pageable);
}