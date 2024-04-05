package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.dao.ProjectDAO;
import org.project.projectmanagementsystem.database.jpa.ProjectJpaRepository;
import org.project.projectmanagementsystem.database.jpa.UserProjectRoleJpaRepository;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.mapper.ProjectMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProjectRepository implements ProjectDAO {
    private final ProjectJpaRepository projectJpaRepository;
    private final UserProjectRoleJpaRepository userProjectRoleJpaRepository;
    private final UserProjectRoleRepository userProjectRoleRepository;

    @Override
    public Optional<Project> findByName(String projectName) {
        return projectJpaRepository.findByName(projectName)
                .map(ProjectMapper.INSTANCE::mapFromEntityToDomain);
    }

    @Override
    public List<Project> findNotFinishedUserProjects(User owner) {
        return userProjectRoleJpaRepository.findNotFinishedUserProjects(owner.getUserId())
                .stream()
                .map(ProjectMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }

    @Override
    public Project addProject(Project createdProject, User owner, Role role) {
        Project savedProject = ProjectMapper.INSTANCE.mapFromEntityToDomain(
                projectJpaRepository.save(ProjectMapper.INSTANCE.mapFromDomainToEntity(createdProject))
        );
        userProjectRoleRepository.addUserProjectRole(owner, savedProject,role);

        return savedProject;
    }

    @Override
    public Optional<Project> findById(UUID projectId) {
        return projectJpaRepository.findById(projectId).map(ProjectMapper.INSTANCE::mapFromEntityToDomain);
    }

    @Override
    public void remove(Project projectToRemove) {
        projectJpaRepository.delete(ProjectMapper.INSTANCE.mapFromDomainToEntity(projectToRemove));
    }
}
