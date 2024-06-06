package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.api.dto.ProjectTaskStatusCount;
import org.project.projectmanagementsystem.database.entities.TaskEntity;
import org.project.projectmanagementsystem.database.jpa.TaskJpaRepository;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.mapper.TaskMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TaskRepository {
    private final TaskJpaRepository taskJpaRepository;
    public Task create(Task task) {
        TaskEntity taskEntity = TaskMapper.INSTANCE.mapFromDomainToEntity(task);
        return TaskMapper.INSTANCE.mapFromEntityToDomain(taskJpaRepository.save(taskEntity));
    }

    public Optional<Task> findByTaskCode(String taskCode) {
        return taskJpaRepository.findByTaskCode(taskCode).map(TaskMapper.INSTANCE::mapFromEntityToDomain);
    }

    public void save(Task task) {
        taskJpaRepository.save(TaskMapper.INSTANCE.mapFromDomainToEntity(task));
    }

    public List<Task> findProjectTasksWithStatus(UUID projectId, EnumSet<Task.TaskStatus> taskStatuses, Pageable pageable) {
        return taskJpaRepository.findProjectTasksWithStatus(projectId,taskStatuses,pageable).stream()
                .map(TaskMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }

    public void remove(Task taskToRemove) {
        taskJpaRepository.deleteById(taskToRemove.getTaskId());
    }

    public List<ProjectTaskStatusCount> findAllProjectTasksGrouped(UUID projectId) {
        return taskJpaRepository.findAllProjectTasksGrouped(projectId);
    }

    public List<Task> findPagedMemberTasks(UUID projectId, String username, Pageable pageable) {
        return taskJpaRepository.findPagedMemberTasks(projectId, username,pageable).stream()
                .map(TaskMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }

    public Long countFinishedUserTasks(User user, UUID projectId) {
        return taskJpaRepository.countFinishedUserTasks(user.getUsername(), projectId);
    }
}
