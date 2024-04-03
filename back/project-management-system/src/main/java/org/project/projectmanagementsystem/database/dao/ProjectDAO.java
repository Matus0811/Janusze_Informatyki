package org.project.projectmanagementsystem.database.dao;


import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.domain.User;

import java.util.List;
import java.util.Optional;

public interface ProjectDAO {
    Optional<Project> findByName(String projectName);

    List<Project> findNotFinishedUserProjects(User owner);

    Project addProject(Project createdProject, User owner, Role role);
}
