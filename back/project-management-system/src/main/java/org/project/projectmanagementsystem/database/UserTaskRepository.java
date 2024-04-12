package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.entities.UserTaskEntity;
import org.project.projectmanagementsystem.database.jpa.UserTaskJpaRepository;
import org.project.projectmanagementsystem.domain.UserTask;
import org.project.projectmanagementsystem.domain.mapper.UserTaskMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserTaskRepository {
    private final UserTaskJpaRepository userTaskJpaRepository;
    public UserTask save(UserTask userTask) {
        UserTaskEntity userTaskEntity = UserTaskMapper.INSTANCE.mapFromDomainToEntity(userTask);

        return UserTaskMapper.INSTANCE.mapFromEntityToDomain(userTaskJpaRepository.save(userTaskEntity));
    }
}
