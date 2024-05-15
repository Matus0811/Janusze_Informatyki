package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.jpa.UserProjectRoleJpaRepository;
import org.project.projectmanagementsystem.domain.UserProjectRole;
import org.project.projectmanagementsystem.domain.mapper.UserProjectRoleMapper;
import org.springframework.data.domain.Pageable;
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

    public List<UserProjectRole> findUnassignedUsersToProjectWhereUsernameStartsWith(UUID projectId, String word, Pageable pageable) {
        return userProjectRoleJpaRepository.findUsersUnassignedToProject(projectId,word,pageable)
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

    public List<UserProjectRole> findAllUserProjectsAsMember(String email, Pageable pageable) {
        return userProjectRoleJpaRepository.findAllUserProjectsAsMember(email,pageable)
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

    public List<UserProjectRole> findPagedProjectMembers(UUID projectId, Pageable pageable) {
        return userProjectRoleJpaRepository.findPagedProjectMembers(projectId,pageable)
                .stream().map(UserProjectRoleMapper.INSTANCE::mapFromEntityToDomain)
                .collect(Collectors.toList());
    }

    public List<UserProjectRole> findPagedProjectMembersWithGivenUsernameNotIncludeUsersIdsInCurrentTask(
            UUID projectId,
            String username,
            Pageable pageable,
            List<Long> allAssignedUserToTaskIds
    ) {
        return userProjectRoleJpaRepository.findPagedProjectMembersWithGivenUsernameNotIncludeUsersIdsInCurrentTask(
                projectId,username,pageable,allAssignedUserToTaskIds
        ).stream()
                .map(UserProjectRoleMapper.INSTANCE::mapFromEntityToDomain).toList();
    }

    public Long countProjectMembers(UUID projectId) {
        return userProjectRoleJpaRepository.countProjectMembers(projectId);
    }

    public Optional<String> findOwnerProjectName(String name, String email) {
        return userProjectRoleJpaRepository.findOwnerProjectName(name,email);
    }
}
