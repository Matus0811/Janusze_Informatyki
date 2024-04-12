package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.entities.ProjectEntity;
import org.project.projectmanagementsystem.database.entities.RoleEntity;
import org.project.projectmanagementsystem.database.entities.UserEntity;
import org.project.projectmanagementsystem.database.entities.UserProjectRoleEntity;
import org.project.projectmanagementsystem.database.jpa.UserProjectRoleJpaRepository;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.UserProjectRole;
import org.project.projectmanagementsystem.domain.mapper.ProjectMapper;
import org.project.projectmanagementsystem.domain.mapper.RoleMapper;
import org.project.projectmanagementsystem.domain.mapper.UserMapper;
import org.project.projectmanagementsystem.domain.mapper.UserProjectRoleMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserProjectRoleRepository {
    private final UserProjectRoleJpaRepository userProjectRoleJpaRepository;

    public UserProjectRole addUserProjectRole(UserProjectRole userProjectRoleToSave) {
        return UserProjectRoleMapper.INSTANCE.mapFromEntity(
                userProjectRoleJpaRepository.saveAndFlush(
                        UserProjectRoleMapper.INSTANCE.mapFromDomain(userProjectRoleToSave)
                )
        );
    }

    public List<UserProjectRole> findUsersUnassignedToProject(UUID projectId) {
        return userProjectRoleJpaRepository.findUsersUnassignedToProject(projectId);
    }
}
