package org.project.projectmanagementsystem.database.dao;

import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.UserProjectRole;

public interface UserProjectRoleDAO {
    UserProjectRole addUserProjectRole(User owner, Project createdProject, Role role);
}
