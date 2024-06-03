package org.project.projectmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.api.dto.ProjectTaskStatusCount;
import org.project.projectmanagementsystem.database.TaskRepository;
import org.project.projectmanagementsystem.domain.*;
import org.project.projectmanagementsystem.services.exceptions.task.TaskException;
import org.project.projectmanagementsystem.services.exceptions.task.TaskNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
            throw new TaskException("Task with HIGH priority should have finish date!", HttpStatus.NOT_FOUND);
        }

        return taskRepository.create(task);
    }

    public Task findByTaskCode(String taskCode) {
        return taskRepository.findByTaskCode(taskCode)
                .orElseThrow(() -> new TaskNotFoundException("Given task not found", HttpStatus.NOT_FOUND));
    }


    public List<Task> findPagedProjectTasksWithStatus(
            UUID projectId,
            EnumSet<Task.TaskStatus> taskStatuses,
            Pageable pageable
    ) {
        return taskRepository.findProjectTasksWithStatus(projectId, taskStatuses,pageable);
    }

    public void deleteTask(String taskCode) {
        Task taskToDelete = findByTaskCode(taskCode);

        if(Task.TaskStatus.FINISHED == taskToDelete.getStatus()){
            throw new TaskException("Cannot remove task which is already finished!",HttpStatus.NOT_FOUND);
        }
        taskRepository.remove(taskToDelete);
    }

    public void save(Task task) {
        taskRepository.save(task);
    }


    public List<ProjectTaskStatusCount> findAllProjectTasksGrouped(UUID projectId) {
        return taskRepository.findAllProjectTasksGrouped(projectId);
    }

    public List<Task> findPagedMemberTasks(UUID projectId, String username, Pageable pageable) {
        return taskRepository.findPagedMemberTasks(projectId,username,pageable);
    }

    public Long countFinishedUserTasks(User user) {
        return taskRepository.countFinishedUserTasks(user);
    }
}
