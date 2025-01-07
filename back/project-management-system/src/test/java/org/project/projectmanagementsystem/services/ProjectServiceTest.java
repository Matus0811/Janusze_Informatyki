package org.project.projectmanagementsystem.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.projectmanagementsystem.TestData;
import org.project.projectmanagementsystem.database.ProjectRepository;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private UserProjectRoleService userProjectRoleService;
    @Mock
    private TaskService taskService;

    @Test
    void createProject() {
        UUID projectId = UUID.randomUUID();
        OffsetDateTime createdDate = OffsetDateTime.now();
        User owner = TestData.user1();
        Role role = TestData.role1();
        Project projectToCreate = TestData.project1()
                .withProjectStatus(Project.ProjectStatus.INITIAL)
                .withStartDate(createdDate)
                .withFinishDate(null);

        Project expected = TestData.project1()
                .withProjectId(projectId)
                .withProjectStatus(Project.ProjectStatus.INITIAL)
                .withStartDate(createdDate)
                .withFinishDate(null);

        Mockito.when(projectRepository.findNotFinishedUserProjectsPaged(Mockito.anyString(), Mockito.any(Pageable.class)))
                .thenReturn(List.of());
        Mockito.when(roleService.findRoleByName("PROJECT_OWNER")).thenReturn(role);
        Mockito.when(projectRepository.addProject(projectToCreate)).thenReturn(projectToCreate.withProjectId(projectId));
        Mockito.when(userProjectRoleService.addUserProjectRole(owner, expected, role)).thenReturn(null);

        //when
        Project project = projectService.createProject(projectToCreate, owner);

        //then
        Assertions.assertEquals(expected, project);
    }

    @Test
    void findNotFinishedOwnerProjects() {
        User owner = TestData.user1();
        int page = 0;
        int pageSize = 6;
        Pageable pageable = PageRequest.of(page,pageSize).withSort(Sort.by("project.startDate").descending());

        Mockito.when(projectRepository.findNotFinishedUserProjectsPaged(Mockito.anyString(), Mockito.any(Pageable.class)))
                .thenReturn(List.of(TestData.project1(), TestData.project2()));

        List<Project> result = projectService.findNotFinishedOwnerProjects(owner.getEmail(), pageable);

        Assertions.assertEquals(2,result.size());
    }

    @Test
    void removeProject() {
    }

    @Test
    void findById() {
    }

    @Test
    void updateProjectStatus() {
    }

    @Test
    void processProjectFinishing() {
    }
}