package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.entity.Project;
import com.accenture.codingtest.springbootcodingtest.entity.User;
import com.accenture.codingtest.springbootcodingtest.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    public List<Project> getAllProjects(String q, int pageIndex, int pageSize, String sortBy, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(direction, sortBy));
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(orders));
        Page<Project> pageProjects;
        if (q == null)
            pageProjects = projectRepository.findAll(pageable);
        else
            pageProjects = projectRepository.findAllByNameContaining(q, pageable);
        return pageProjects.getContent();
    }

    public Project getProjectById(UUID id) throws Exception {
        Optional<Project> existingProject = projectRepository.findById(id);
        if (existingProject.isPresent())
            return existingProject.get();
        else
            throw new Exception("User not found");
    }

    public Project createProject(Project project) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<Project> projectByName = projectRepository.findProjectByName(project.getName());
        if (projectByName.isPresent())
            throw new Exception("Project name has been used");
        else {
            Project _project = new Project();
            _project.setName(project.getName());
            _project.setCreated_by(currentPrincipalName);
            projectRepository.save(_project);
            return _project;
        }
    }

    public Project updateProject(UUID id, Project project) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<Project> _project = projectRepository.findById(id);
        if (_project.isPresent()) {
            Optional<Project> projectByName = projectRepository.findProjectByName(project.getName());
            if (projectByName.isPresent())
                throw new Exception("Project name has been used");
            else {
                Project existingProject = _project.get();
                existingProject.setName(project.getName());
                existingProject.setCreated_by(currentPrincipalName);
                projectRepository.save(existingProject);
                return existingProject;
            }
        } else
            throw new Exception("Project not found");
    }

    public Project patchProject(UUID id, Project project) throws Exception {
        boolean requiredUpdate = false;
        Project existingProject = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<Project> _project = projectRepository.findById(id);
        if (_project.isPresent()) {
            existingProject = _project.get();
            if (StringUtils.hasLength(project.getName())) {
                Optional<Project> projectByName = projectRepository.findProjectByName(project.getName());
                if (projectByName.isPresent())
                    throw new Exception("Project name has been used");
                else {
                    existingProject.setName(project.getName());
                    existingProject.setCreated_by(currentPrincipalName);
                    requiredUpdate = true;
                }
            }
        }
        if (requiredUpdate)
            projectRepository.save(existingProject);
        return existingProject;
    }

    public void deleteProject(UUID id) throws Exception {
        Optional<Project> _project = projectRepository.findById(id);
        if (_project.isPresent())
            projectRepository.deleteById(id);
        else
            throw new Exception("Project not found");
    }
}