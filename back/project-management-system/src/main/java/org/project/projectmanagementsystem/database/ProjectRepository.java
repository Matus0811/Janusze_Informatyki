package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.jpa.ProjectJpaRepository;
import org.project.projectmanagementsystem.database.jpa.UserProjectRoleJpaRepository;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.mapper.ProjectMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {
    private final ProjectJpaRepository projectJpaRepository;
    private final UserProjectRoleJpaRepository userProjectRoleJpaRepository;
    private final UserProjectRoleRepository userProjectRoleRepository;

    public Optional<Project> findByName(String projectName) {
        return projectJpaRepository.findByName(projectName)
                .map(ProjectMapper.INSTANCE::mapFromEntityToDomain);
    }

    public List<Project> findNotFinishedUserProjects(User owner) {
        return userProjectRoleJpaRepository.findNotFinishedUserProjects(owner.getUserId())
                .stream()
                .map(ProjectMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }

    public Project addProject(Project createdProject) {
        return ProjectMapper.INSTANCE.mapFromEntityToDomain(
                projectJpaRepository.save(ProjectMapper.INSTANCE.mapFromDomainToEntity(createdProject))
        );
    }

    public Optional<Project> findById(UUID projectId) {
        return projectJpaRepository.findById(projectId).map(ProjectMapper.INSTANCE::mapFromEntityToDomain);
    }

    public void remove(Project projectToRemove) {
        projectJpaRepository.delete(ProjectMapper.INSTANCE.mapFromDomainToEntity(projectToRemove));
    }

    public void updateProjectStatus(Project project) {
        projectJpaRepository.saveAndFlush(ProjectMapper.INSTANCE.mapFromDomainToEntity(project));
    }
}
