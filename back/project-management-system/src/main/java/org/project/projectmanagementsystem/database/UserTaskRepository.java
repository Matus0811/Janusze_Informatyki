package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.entities.TaskEntity;
import org.project.projectmanagementsystem.database.entities.UserTaskEntity;
import org.project.projectmanagementsystem.database.jpa.UserTaskJpaRepository;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.UserTask;
import org.project.projectmanagementsystem.domain.mapper.UserTaskMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserTaskRepository {
    private final UserTaskJpaRepository userTaskJpaRepository;
    public UserTask save(UserTask userTask) {
        UserTaskEntity userTaskEntity = UserTaskMapper.INSTANCE.mapFromDomainToEntity(userTask);

        return UserTaskMapper.INSTANCE.mapFromEntityToDomain(userTaskJpaRepository.save(userTaskEntity));
    }

    public Optional<UserTask> findUserTask(Long taskId, Long userId) {
        return userTaskJpaRepository.findByTaskIdAndUserId(taskId,userId)
                .map(UserTaskMapper.INSTANCE::mapFromEntityToDomain);
    }

    public List<UserTask> findNotFinished(String taskCode, UUID projectId) {
        return userTaskJpaRepository.findNotFinishedUserTask(taskCode,projectId)
                .stream()
                .map(UserTaskMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }

    public List<UserTask> findAllUserTasksAssignedToUserInProject(Long userId, UUID projectId) {
        return userTaskJpaRepository.findAllUserTasksAssignedToUserInProject(userId, projectId)
                .stream()
                .map(UserTaskMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }

    public void remove(UserTask userTask) {
        userTaskJpaRepository.delete(UserTaskMapper.INSTANCE.mapFromDomainToEntity(userTask));
    }
}
