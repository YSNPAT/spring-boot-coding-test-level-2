package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.entity.Task;
import com.accenture.codingtest.springbootcodingtest.repository.ProjectRepository;
import com.accenture.codingtest.springbootcodingtest.repository.TaskRepository;
import com.accenture.codingtest.springbootcodingtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
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

    public Task getTaskById(UUID id) {
        Task _task = taskRepository.findById(id).orElseThrow();
        return _task;
    }

    public void createTask(Task task) {
        projectRepository.findById(task.getProject_id()).orElseThrow();
        userRepository.findById(task.getUser_id()).orElseThrow();
        Task _task = new Task();
        _task.setTitle(task.getTitle());
        _task.setDescription(task.getDescription());
        _task.setStatus("NOT_STARTED");
        _task.setProject_id(task.getProject_id());
        _task.setUser_id(task.getUser_id());
        taskRepository.save(_task);
    }

    public void updateTask(UUID id, Task task) {
        Task _task = taskRepository.findById(id).orElseThrow();
        projectRepository.findById(task.getProject_id()).orElseThrow();
        userRepository.findById(task.getUser_id()).orElseThrow();
        _task.setTitle(task.getTitle());
        _task.setDescription(task.getDescription());
        _task.setStatus(task.getStatus());
        _task.setProject_id(task.getProject_id());
        _task.setUser_id(task.getUser_id());
        taskRepository.save(_task);
    }

    public void patchTask(UUID id, Task task) {
        Task _task = taskRepository.findById(id).orElseThrow();
        projectRepository.findById(task.getProject_id()).orElseThrow();
        userRepository.findById(task.getUser_id()).orElseThrow();
        boolean requiredUpdate = false;
        if (StringUtils.hasLength(task.getTitle())) {
            _task.setTitle(task.getTitle());
            requiredUpdate = true;
        }
        if (StringUtils.hasLength(task.getDescription())) {
            _task.setDescription(task.getDescription());
            requiredUpdate = true;
        }
        if (StringUtils.hasLength(task.getStatus())) {
            _task.setStatus(task.getStatus());
            requiredUpdate = true;
        }
        if (StringUtils.hasLength(task.getProject_id().toString())) {
            _task.setProject_id(task.getProject_id());
            requiredUpdate = true;
        }
        if (StringUtils.hasLength(task.getUser_id().toString())) {
            _task.setUser_id(task.getUser_id());
            requiredUpdate = true;
        }
        if (requiredUpdate)
            taskRepository.save(_task);
    }

    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}