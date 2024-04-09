package org.project.projectmanagementsystem.domain;

import org.project.projectmanagementsystem.database.entities.ProjectEntity;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

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
    ProjectEntity project;
    Set<Comment> comments;
    Set<UserTask> userTasks;

    public static Task buildTaskFromTaskForm(TaskForm taskForm){
        Task task = Task.builder()
                .description(taskForm.getDescription())
                .status(TaskStatus.valueOf(taskForm.getStatus()))
                .priority(Priority.valueOf(taskForm.getPriority()))
                .startDate(OffsetDateTime.now())
                .build();

        if(!taskForm.getFinishDate().isBlank()){
            task = task.withFinishDate(OffsetDateTime.parse(taskForm.getFinishDate()));
        }

        return task;
    }

    public enum TaskStatus{
        CONCEPT, IN_PROGRESS, FINISHED, REJECTED, BUG, TO_DO
    }

    public enum Priority{
        LOW,MEDIUM,HIGH
    }
}
