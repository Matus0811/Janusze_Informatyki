package org.project.projectmanagementsystem.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.TaskRepository;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.services.exceptions.task.TaskException;
import org.project.projectmanagementsystem.services.exceptions.task.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task createTask(Task task) {
        Task.Priority priority = task.getPriority();

        if (priority == Task.Priority.HIGH
            && (Objects.isNull(task.getFinishDate()) || task.getFinishDate().toString().isBlank())) {
            throw new TaskException("Task with HIGH priority should have finish date!", HttpStatus.NOT_ACCEPTABLE);
        }

        return taskRepository.addTask(task);
    }

    public Task findByTaskCode(String taskCode) {
        return taskRepository.findByTaskCode(taskCode)
                .orElseThrow(() -> new TaskNotFoundException("Given task not found", HttpStatus.NOT_FOUND));
    }


    public List<Task> findProjectTasksWithStatus(UUID projectId, EnumSet<Task.TaskStatus> taskStatuses) {
        return taskRepository.findProjectTasksWithStatus(projectId, taskStatuses);
    }

    public void deleteTask(String taskCode) {
        Task taskToDelete = findByTaskCode(taskCode);
        //TODO mozna usunąć tylko te, które są UNFINISHED
        taskRepository.remove(taskToDelete);
    }

    public void save(Task task) {
        taskRepository.save(task);
    }
}
