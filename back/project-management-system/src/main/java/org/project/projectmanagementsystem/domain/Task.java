package org.project.projectmanagementsystem.domain;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@With
@Value
@Builder
public class Task {

    Long taskId;
    String taskCode;
    String name;
    String description;
    Task.TaskStatus status;
    Task.Priority priority;
    OffsetDateTime startDate;
    OffsetDateTime finishDate;
    Project project;
    Set<Comment> comments;
    Set<UserTask> userTasks;

    public static Task buildTaskFromTaskForm(TaskForm taskForm, Project project) {
        Task task = Task.builder()
                .taskCode(UUID.randomUUID().toString())
                .name(taskForm.getName())
                .description(taskForm.getDescription())
                .status(taskForm.getStatus())
                .priority(taskForm.getPriority())
                .startDate(OffsetDateTime.now())
                .project(project)
                .build();

        if (existsTaskFinishDate(taskForm)) {
            if (isTaskFinishDateAfterProjectFinishDate(taskForm, project)) {
                task = task.withFinishDate(project.getFinishDate());
            } else {
                task = task.withFinishDate(taskForm.getFinishDate());
            }
        }

        return task;
    }

    private static boolean existsTaskFinishDate(TaskForm taskForm) {
        return !Objects.isNull(taskForm.getFinishDate()) && !taskForm.getFinishDate().toString().isBlank();
    }

    private static boolean isTaskFinishDateAfterProjectFinishDate(TaskForm taskForm, Project project) {
        return !Objects.isNull(project.getFinishDate()) && taskForm.getFinishDate().isAfter(project.getFinishDate());
    }

    public static Task buildBugTask() {
        return Task.builder()
                .taskCode(UUID.randomUUID().toString())
                .status(TaskStatus.BUG)
                .priority(Priority.MEDIUM)
                .startDate(OffsetDateTime.now())
                .build();
    }

    public enum TaskStatus {
        CONCEPT, IN_PROGRESS, FINISHED, REJECTED, BUG, TO_DO
    }

    public enum Priority {
        LOW, MEDIUM, HIGH
    }
}
