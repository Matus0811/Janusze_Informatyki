package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.jpa.UserProjectRoleJpaRepository;
import org.project.projectmanagementsystem.domain.UserProjectRole;
import org.project.projectmanagementsystem.domain.mapper.UserProjectRoleMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserProjectRoleRepository {
    private final UserProjectRoleJpaRepository userProjectRoleJpaRepository;

    public UserProjectRole addUserProjectRole(UserProjectRole userProjectRoleToSave) {
        return UserProjectRoleMapper.INSTANCE.mapFromEntityToDomain(
                userProjectRoleJpaRepository.saveAndFlush(
                        UserProjectRoleMapper.INSTANCE.mapFromDomainToEntity(userProjectRoleToSave)
                )
        );
    }

    public List<UserProjectRole> findUsersUnassignedToProject(UUID projectId) {
        return userProjectRoleJpaRepository.findUsersUnassignedToProject(projectId)
                .stream()
                .map(UserProjectRoleMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }

    public void removeUserProjectRole(UserProjectRole userProjectRole) {
        userProjectRoleJpaRepository.delete(UserProjectRoleMapper.INSTANCE.mapFromDomainToEntity(userProjectRole));
    }

    public Optional<UserProjectRole> findUserProjectRole(UUID projectId, String userEmail) {
        return userProjectRoleJpaRepository.findUserProjectRole(projectId, userEmail)
                .map(UserProjectRoleMapper.INSTANCE::mapFromEntityToDomain);
    }

    public List<UserProjectRole> findAllUserProjectsAsMember(String email) {
        return userProjectRoleJpaRepository.findAllUserProjectsAsMember(email)
                .stream()
                .map(UserProjectRoleMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }

    public List<UserProjectRole> findAllUserRoles(Long userId) {
        return userProjectRoleJpaRepository.findAllUserRoles(userId)
                .stream().map(UserProjectRoleMapper.INSTANCE::mapFromEntityToDomain)
                .collect(Collectors.toList());
    }

    public void removeDefaultUserRole(Long userId) {
        userProjectRoleJpaRepository.removeDefaultUserRole(userId);
    }

    public List<UserProjectRole> findAllUserProjectsAsOwner(String email) {
        return userProjectRoleJpaRepository.findAllUserProjectsAsOwner(email)
                .stream().map(UserProjectRoleMapper.INSTANCE::mapFromEntityToDomain)
                .collect(Collectors.toList());
    }
}
