package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.constant.TaskStatus;
import com.accenture.codingtest.springbootcodingtest.entity.Project;
import com.accenture.codingtest.springbootcodingtest.entity.Task;
import com.accenture.codingtest.springbootcodingtest.entity.User;
import com.accenture.codingtest.springbootcodingtest.repository.ProjectRepository;
import com.accenture.codingtest.springbootcodingtest.repository.TaskRepository;
import com.accenture.codingtest.springbootcodingtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(UUID id) throws Exception {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent())
            return existingTask.get();
        else
            throw new Exception("Task not found");

    }

    public Task createTask(Task task) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<Project> _project = projectRepository.findById(task.getProject_id());
        if (_project.isPresent()) {
            if (_project.get().getCreated_by().equals(currentPrincipalName)) {
                Optional<User> _user = userRepository.findById(task.getUser_id());
                if (_user.isPresent()) {
                    Task newTask = new Task();
                    newTask.setTitle(task.getTitle());
                    newTask.setDescription(task.getDescription());
                    newTask.setStatus(TaskStatus.NOT_STARTED.name());
                    newTask.setProject_id(task.getProject_id());
                    newTask.setUser_id(task.getUser_id());
                    taskRepository.save(newTask);
                    return newTask;
                } else
                    throw new Exception("User not found");
            } else
                throw new Exception("Task can only be assign by the project creator");
        } else
            throw new Exception("Project not found");
    }

    public Task updateTask(UUID id, Task task) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<Task> _task = taskRepository.findById(id);
        if (_task.isPresent()) {
            Optional<Project> _project = projectRepository.findById(task.getProject_id());
            if (_project.isPresent()) {
                Optional<User> _user = userRepository.findById(task.getUser_id());
                if (_user.isPresent()) {
                    if (contains(task.getStatus())) {
                        if (_project.get().getCreated_by().equals(currentPrincipalName) || _user.get().getUsername().equals(currentPrincipalName)) {
                            Task existingTask = _task.get();
                            existingTask.setTitle(task.getTitle());
                            existingTask.setDescription(task.getDescription());
                            existingTask.setStatus(task.getStatus());
                            existingTask.setProject_id(task.getProject_id());
                            existingTask.setUser_id(task.getUser_id());
                            taskRepository.save(existingTask);
                            return existingTask;
                        } else
                            throw new Exception("Task can only be update by the project creator or team member");
                    } else
                        throw new Exception("Status can only be: 'NOT_STARTED' or 'IN_PROGRESS' or 'READY_FOR_TEST' or 'COMPLETED'");
                } else
                    throw new Exception("User not found");
            } else
                throw new Exception("Project not found");
        } else
            throw new Exception("Task not found");
    }

    public Task patchTask(UUID id, Task task) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<Task> _task = taskRepository.findById(id);
        if (_task.isPresent()) {
            Optional<Project> _project = projectRepository.findById(task.getProject_id());
            if (_project.isPresent()) {
                Optional<User> _user = userRepository.findById(task.getUser_id());
                if (_user.isPresent()) {
                    if (contains(task.getStatus())) {
                        boolean requiredUpdate = false;
                        Task existingTask = _task.get();
                        if (_project.get().getCreated_by().equals(currentPrincipalName) || _user.get().getUsername().equals(currentPrincipalName)) {
                            if (StringUtils.hasLength(task.getTitle())) {
                                existingTask.setTitle(task.getTitle());
                                requiredUpdate = true;
                            }
                            if (StringUtils.hasLength(task.getDescription())) {
                                existingTask.setDescription(task.getDescription());
                                requiredUpdate = true;
                            }
                            if (StringUtils.hasLength(task.getStatus())) {
                                existingTask.setStatus(task.getStatus());
                                requiredUpdate = true;
                            }
                            if (StringUtils.hasLength(task.getProject_id().toString())) {
                                existingTask.setProject_id(task.getProject_id());
                                requiredUpdate = true;
                            }
                            if (StringUtils.hasLength(task.getUser_id().toString())) {
                                existingTask.setUser_id(task.getUser_id());
                                requiredUpdate = true;
                            }
                            if (requiredUpdate)
                                taskRepository.save(existingTask);
                            return existingTask;
                        } else
                            throw new Exception("Task can only be update by the project creator or team member");
                    } else
                        throw new Exception("Status can only be: 'NOT_STARTED' or 'IN_PROGRESS' or 'READY_FOR_TEST' or 'COMPLETED'");
                } else
                    throw new Exception("User not found");
            } else
                throw new Exception("Project not found");
        } else
            throw new Exception("Task not found");
    }

    public void deleteTask(UUID id) throws Exception {
        Optional<Task> _task = taskRepository.findById(id);
        if (_task.isPresent())
            taskRepository.deleteById(id);
        else
            throw new Exception("Task not found");

    }

    private boolean contains(String name) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.name().equals(name))
                return true;
        }
        return false;
    }
}