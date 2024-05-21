package org.project.projectmanagementsystem;

import org.project.projectmanagementsystem.domain.*;

import java.time.OffsetDateTime;
import java.util.UUID;

public class TestData {
    public static User user1(){
        return User.builder()
                .username("username1")
                .password("password1")
                .name("name1")
                .surname("surname1")
                .gender(User.Gender.MALE)
                .email("user1@gmail.com")
                .phone("+48 111 111 111")
                .build();
    }

    public static Project project1(){
        return Project.builder()
                .projectId(UUID.randomUUID())
                .name("Some name 1")
                .description("Some project description")
                .projectStatus(Project.ProjectStatus.IN_PROGRESS)
                .startDate(OffsetDateTime.now())
                .finishDate(OffsetDateTime.now().plusYears(2))
                .build();
    }

    public static Project project2(){
        return Project.builder()
                .projectId(UUID.randomUUID())
                .name("Some name 2")
                .description("Some project description2")
                .projectStatus(Project.ProjectStatus.INITIAL)
                .startDate(OffsetDateTime.now())
                .build();
    }

    public static BugForm someBugForm1(){
        return BugForm.builder()
                .title("Example bug 1")
                .description("Some description")
                .userEmail("user1@gmail.com")
                .projectId(UUID.randomUUID())
                .bugType(Bug.BugType.LOGICAL)
                .build();
    }

    public static Task task1() {
        return Task.builder()
                .taskId(1L)
                .taskCode(UUID.randomUUID().toString())
                .status(Task.TaskStatus.BUG)
                .priority(Task.Priority.MEDIUM)
                .project(project1())
                .startDate(OffsetDateTime.now())
                .build();
    }

    public static Role role1() {
        return Role.builder()
                .roleId(1L)
                .name("PROJECT_OWNER")
                .build();
    }
}
