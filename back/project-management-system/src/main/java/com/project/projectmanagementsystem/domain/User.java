package com.project.projectmanagementsystem.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.Set;

@With
@Value
@Builder
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
