package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.dao.ProjectDAO;
import org.project.projectmanagementsystem.database.entities.ProjectEntity;
import org.project.projectmanagementsystem.database.jpa.ProjectJpaRepository;
import org.project.projectmanagementsystem.database.jpa.UserProjectRoleJpaRepository;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.mapper.ProjectMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProjectRepository implements ProjectDAO {
    private final ProjectJpaRepository projectJpaRepository;
    private final UserProjectRoleJpaRepository userProjectRoleJpaRepository;
    private final UserRepository userRepository;
    private final UserProjectRoleRepository userProjectRoleRepository;

    @Override
    public Optional<Project> findByName(String projectName) {
        return projectJpaRepository.findByName(projectName)
                .map(ProjectMapper.INSTANCE::mapFromEntity);
    }

    @Override
    public List<Project> findNotFinishedUserProjects(User owner) {
        return userProjectRoleJpaRepository.findNotFinishedUserProjects(owner.getUserId())
                .stream()
                .map(ProjectMapper.INSTANCE::mapFromEntity)
                .toList();
    }

    @Override
    public Project addProject(Project createdProject, User owner, Role role) {
        ProjectEntity project = ProjectMapper.INSTANCE.mapFromDomain(createdProject);

        Project savedProject = ProjectMapper.INSTANCE.mapFromEntity(projectJpaRepository.save(project));
        userProjectRoleRepository.addUserProjectRole(owner, savedProject,role);

        return savedProject;
    }
}
