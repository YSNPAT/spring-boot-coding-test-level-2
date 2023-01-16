package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.entity.Project;
import com.accenture.codingtest.springbootcodingtest.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(UUID id) {
        return projectRepository.findById(id).orElseThrow();
    }

    public void createProject(Project project) throws Exception {
        Optional<Project> projectByName = projectRepository.findProjectByName(project.getName());
        if (projectByName.isPresent())
            throw new Exception("Project name has been used");
        projectRepository.save(project);
    }

    public void updateProject(UUID id, Project project) throws Exception {
        Project _project = projectRepository.findById(id).orElseThrow();
        Optional<Project> projectByName = projectRepository.findProjectByName(project.getName());
        if (projectByName.isPresent())
            throw new Exception("Project name has been used");
        _project.setName(project.getName());
        projectRepository.save(_project);
    }

    public void patchProject(UUID id, Project project) throws Exception {
        Project _project = projectRepository.findById(id).orElseThrow();
        boolean requiredUpdate = false;
        if (StringUtils.hasLength(project.getName())) {
            Optional<Project> projectByName = projectRepository.findProjectByName(project.getName());
            if (projectByName.isPresent())
                throw new Exception("Project name has been used");
            _project.setName(project.getName());
            requiredUpdate = true;
        }
        if (requiredUpdate)
            projectRepository.save(_project);
    }

    public void deleteProject(UUID id) {
        projectRepository.deleteById(id);
    }
}