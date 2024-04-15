package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.entities.TaskEntity;
import org.project.projectmanagementsystem.database.jpa.TaskJpaRepository;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.mapper.TaskMapper;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TaskRepository {
    private final TaskJpaRepository taskJpaRepository;
    public Task addTask(Task task) {
        TaskEntity taskEntity = TaskMapper.INSTANCE.mapFromDomainToEntity(task);
        return TaskMapper.INSTANCE.mapFromEntityToDomain(taskJpaRepository.save(taskEntity));
    }

    public Optional<Task> findByTaskCode(String taskCode) {
        return taskJpaRepository.findByTaskCode(taskCode).map(TaskMapper.INSTANCE::mapFromEntityToDomain);
    }

    public void save(Task task) {
        taskJpaRepository.save(TaskMapper.INSTANCE.mapFromDomainToEntity(task));
    }

    public List<Task> findProjectTasksWithStatus(UUID projectId, EnumSet<Task.TaskStatus> taskStatuses) {
        return taskJpaRepository.findProjectTasksWithStatus(projectId,taskStatuses).stream()
                .map(TaskMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }
}
