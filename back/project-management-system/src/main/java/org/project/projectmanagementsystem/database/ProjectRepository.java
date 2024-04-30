package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.jpa.ProjectJpaRepository;
import org.project.projectmanagementsystem.database.jpa.UserProjectRoleJpaRepository;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.mapper.ProjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {
    private final ProjectJpaRepository projectJpaRepository;
    private final UserProjectRoleJpaRepository userProjectRoleJpaRepository;

    public Optional<Project> findByName(String projectName) {
        return projectJpaRepository.findByName(projectName)
                .map(ProjectMapper.INSTANCE::mapFromEntityToDomain);
    }

    public List<Project> findNotFinishedUserProjectsPaged(String ownerEmail, Pageable pageable) {;
        return userProjectRoleJpaRepository.findNotFinishedUserProjects(ownerEmail,pageable)
                .stream()
                .map(ProjectMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }
    public List<Project> findNotFinishedUserProjects(String ownerEmail) {
        return userProjectRoleJpaRepository.findNotFinishedUserProjects(ownerEmail,Pageable.unpaged())
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
        projectJpaRepository.deleteById(projectToRemove.getProjectId());
    }

    public void updateProjectStatus(Project project) {
        projectJpaRepository.saveAndFlush(ProjectMapper.INSTANCE.mapFromDomainToEntity(project));
    }

    public void save(Project projectToSave) {
        projectJpaRepository.save(ProjectMapper.INSTANCE.mapFromDomainToEntity(projectToSave));
    }


}
