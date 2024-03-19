package com.project.projectmanagementsystem.domain;

import com.project.projectmanagementsystem.database.entities.BugEntity;
import com.project.projectmanagementsystem.database.entities.CommentEntity;
import com.project.projectmanagementsystem.database.entities.UserProjectRoleEntity;
import com.project.projectmanagementsystem.database.entities.UserTaskEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of= {"userId","username","email"})
public class User {
    Long userId;
    String username;
    String password;
    String name;
    String surname;
    Gender gender;
    String email;
    String phone;
    Set<UserProjectRole> projectRoles;
    Set<Comment> comments;
    Set<UserTask> userTasks;
    Set<Bug> reportedBugs;

    public enum Gender{
        MALE,FEMALE
    }
}
