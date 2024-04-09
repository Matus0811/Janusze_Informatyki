package org.project.projectmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.TaskRepository;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.TaskForm;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final ProjectService projectService;
    private final TaskRepository taskRepository;

    public Task processTaskCreation(TaskForm taskForm) {
        Project project = projectService.findById(UUID.fromString(taskForm.getProjectId()));

        Task task = Task.buildTaskFromTaskForm(taskForm);
        //

        return taskRepository.addTask(task);
    }
}
