package org.project.projectmanagementsystem.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.projectmanagementsystem.TestData;
import org.project.projectmanagementsystem.database.BugRepository;
import org.project.projectmanagementsystem.domain.*;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class BugServiceTest {

    @InjectMocks
    private BugService bugService;
    @Mock
    private BugRepository bugRepository;
    @Mock
    private ProjectService projectService;
    @Mock
    private UserService userService;
    @Mock
    private TaskService taskService;

    @Test
    void createBug() {
        //given
        UUID projectId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        BugForm bugForm = TestData.someBugForm1()
                .withProjectId(projectId)
                .withTaskCode(taskId);
        Project projectWithBug = TestData.project1();
        User user = TestData.user1();
        Task createdTask = Task.buildBugTask()
                .withTaskCode(taskId.toString())
                .withName(bugForm.getTitle())
                .withDescription(bugForm.getDescription())
                .withProject(projectWithBug);

        Bug expected = Bug.buildBug(bugForm, user, projectWithBug, createdTask);

        Mockito.when(projectService.findById(Mockito.any(UUID.class))).thenReturn(projectWithBug);
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(user);
        Mockito.when(taskService.findByTaskCode(Mockito.anyString())).thenReturn(createdTask.withTaskId(1L));
        Mockito.when(bugRepository.save(Mockito.any(Bug.class))).thenReturn(expected);
        //when
        Bug result = bugService.createBug(bugForm);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void findBugForTask() {
        //given
        Task createdTask = TestData.task1();
        Bug bug = Bug.buildBug(TestData.someBugForm1(), TestData.user1(), TestData.project1(), TestData.task1());

        Mockito.when(bugRepository.findBugForTask(createdTask)).thenReturn(Optional.of(bug));

        //when
        Bug bugForTask = bugService.findBugForTask(createdTask);

        //then
        Assertions.assertEquals(bug, bugForTask);
    }

}