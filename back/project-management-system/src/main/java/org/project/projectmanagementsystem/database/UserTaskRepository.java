package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.entities.UserTaskEntity;
import org.project.projectmanagementsystem.database.jpa.UserTaskJpaRepository;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.UserTask;
import org.project.projectmanagementsystem.domain.UserTasks;
import org.project.projectmanagementsystem.domain.mapper.UserTaskMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserTaskRepository {
    private final UserTaskJpaRepository userTaskJpaRepository;
    public UserTask save(UserTask userTask) {
        UserTaskEntity userTaskEntity = UserTaskMapper.INSTANCE.mapFromDomainToEntity(userTask);

        return UserTaskMapper.INSTANCE.mapFromEntityToDomain(userTaskJpaRepository.save(userTaskEntity));
    }

    public Optional<UserTask> findUserTask(String taskCode, String userEmail) {
        return userTaskJpaRepository.findByTaskCodeAndUserUsername(taskCode,userEmail)
                .map(UserTaskMapper.INSTANCE::mapFromEntityToDomain);
    }

    public List<UserTask> findNotFinished(String taskCode, UUID projectId) {
        return userTaskJpaRepository.findNotFinishedUserTask(taskCode,projectId)
                .stream()
                .map(UserTaskMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }

    public List<UserTask> findAllUserTasksAssignedToUserInProject(String userEmail, UUID projectId) {
        return userTaskJpaRepository.findAllUserTasksAssignedToUserInProject(userEmail, projectId)
                .stream()
                .map(UserTaskMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }

    public void remove(UserTask userTask) {
        userTaskJpaRepository.delete(UserTaskMapper.INSTANCE.mapFromDomainToEntity(userTask));
    }

    public void saveAll(List<UserTask> taskUsers) {
        userTaskJpaRepository.saveAll(taskUsers.stream()
                .map(UserTaskMapper.INSTANCE::mapFromDomainToEntity)
                .collect(Collectors.toList())
        );
    }

    public List<UserTask> findPagedUsersAssignedToTask(String taskCode, Pageable pageable) {
        return userTaskJpaRepository.findPagedUsersAssignedToTask(taskCode,pageable).stream()
                .map(UserTaskMapper.INSTANCE::mapFromEntityToDomain)
                .collect(Collectors.toList());
    }

    public List<UserTask> findAllUsersAssignedToTask(String taskCode) {
        return userTaskJpaRepository.findAllUsersAssignedToTask(taskCode).stream()
                .map(UserTaskMapper.INSTANCE::mapFromEntityToDomain)
                .collect(Collectors.toList());
    }

    public List<UserTasks> findFinishedTasksForUsers(Project project) {
        return userTaskJpaRepository.findFinishedTasksForUsersInProject(project.getProjectId());
    }

    public List<UserTask> findPagedMemberTasks(UUID projectId, String username, Pageable pageable) {
        return userTaskJpaRepository.findPagedMemberTasks(projectId,username,pageable).stream()
                .map(UserTaskMapper.INSTANCE::mapFromEntityToDomain).toList();
    }
}
