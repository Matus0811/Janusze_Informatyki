package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.dao.UserProjectRoleDAO;
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

@Repository
@RequiredArgsConstructor
public class UserProjectRoleRepository implements UserProjectRoleDAO {
    private final UserProjectRoleJpaRepository userProjectRoleJpaRepository;
    @Override
    public UserProjectRole addUserProjectRole(User owner, Project createdProject, Role role) {
        RoleEntity roleEntity = RoleMapper.INSTANCE.mapFromDomainToEntity(role);
        UserEntity ownerEntity = UserMapper.INSTANCE.mapFromDomainToEntity(owner);
        ProjectEntity projectEntity = ProjectMapper.INSTANCE.mapFromDomainToEntity(createdProject);

        UserProjectRoleEntity userProjectRoleEntityToSave = UserProjectRoleEntity.builder()
                .user(ownerEntity)
                .project(projectEntity)
                .role(roleEntity)
                .build();

        return UserProjectRoleMapper.INSTANCE.mapFromEntity(
                userProjectRoleJpaRepository.saveAndFlush(userProjectRoleEntityToSave)
        );
    }
}
