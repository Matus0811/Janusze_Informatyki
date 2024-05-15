package org.project.projectmanagementsystem.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.projectmanagementsystem.api.dto.ProjectDTO;
import org.project.projectmanagementsystem.api.dto.ProjectFormDTO;
import org.project.projectmanagementsystem.api.dto.UserDTO;
import org.project.projectmanagementsystem.api.dto.UserFormDTO;
import org.project.projectmanagementsystem.domain.mapper.FormMapper;
import org.project.projectmanagementsystem.domain.mapper.ProjectMapper;
import org.project.projectmanagementsystem.domain.mapper.UserMapper;
import org.project.projectmanagementsystem.services.ProjectService;
import org.project.projectmanagementsystem.services.ProjectTaskService;
import org.project.projectmanagementsystem.services.ProjectUserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectUserService projectUserService;
    private final ProjectTaskService projectTaskService;

    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectFormDTO project) {
        ProjectDTO createdProject = ProjectMapper.INSTANCE
                .mapFromDomainToDto(
                        projectUserService.processProjectCreation(FormMapper.INSTANCE.mapFromDtoToDomain(project))
                );

        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable(name = "projectId") UUID projectId) {

        log.info("Processing project deletion with id: [{}]", projectId);
        projectService.removeProject(projectId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> findAllOwnerProjects(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "email") String email) {
        log.info("Processing searching all projects for owner with email: [{}]", email);

        Pageable pageable = PageRequest.of(page,6).withSort(Sort.by("project.startDate").descending());
        List<ProjectDTO> userProjects = projectService
                .findNotFinishedOwnerProjects(email,pageable)
                .stream()
                .map(ProjectMapper.INSTANCE::mapFromDomainToDto)
                .toList();

        return new ResponseEntity<>(userProjects, HttpStatus.OK);
    }

    @GetMapping("/member-project-list")
    public ResponseEntity<List<ProjectDTO>> findPagedMemberUserProjects(
            @RequestParam(name="page") Integer page,
            @RequestParam(name="email") String email) {
        Pageable pageable = PageRequest.of(page,6).withSort(Sort.by("project.startDate").descending());
        List<ProjectDTO> allUserProjectsAsMember = projectUserService
                .findAllUserProjectsAsMember(email,pageable)
                .stream()
                .map(ProjectMapper.INSTANCE::mapFromDomainToDto)
                .toList();

        return new ResponseEntity<>(allUserProjectsAsMember, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable UUID projectId) {

        log.info("Processing searching project with id:[{}]", projectId);
        ProjectDTO projectDTO = ProjectMapper.INSTANCE.mapFromDomainToDto(projectService.findById(projectId));

        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }

    @PostMapping("/{projectId}/add-users")
    public ResponseEntity<?> addUsersToProject(
            @PathVariable("projectId") UUID projectId,
            @RequestBody List<UserDTO> userDTOS
    ) {
        List<String> usernames = userDTOS.stream().map(UserDTO::username).toList();
        projectUserService.addUsersToProject(projectId,usernames);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{projectId}/finish")
    public ResponseEntity<?> finishProject(@PathVariable("projectId") UUID projectId) {
        projectService.processProjectFinishing(projectId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projectId}/remove-user")
    public ResponseEntity<?> removeUserFromProject(
            @PathVariable("projectId") UUID projectId,
            @RequestParam(name = "memberEmail") String memberEmail
    ) {
        log.info("Processing user deletion for project: [{}], user to delete id: [{}]", projectId, memberEmail);

        projectUserService.removeUserFromProject(projectId,memberEmail);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{projectId}/unassigned-users")
    public ResponseEntity<List<UserDTO>> getUnassignedUsers(
            @PathVariable(name = "projectId") UUID projectId,
            @RequestParam(name="username") String username,
            @RequestParam(name="page") Integer page
    ) {
        log.info("Searching unassigned users to project: [{}]", projectId);
        Pageable pageable = PageRequest.of(page,6).withSort(Sort.by("u.username"));
        List<UserDTO> users = projectUserService.getUnassignedUsers(projectId,username,pageable).stream()
                .map(UserMapper.INSTANCE::mapFromDomainToDto)
                .toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{projectId}/project-members")
    public ResponseEntity<List<UserDTO>> listProjectMembers(
            @PathVariable("projectId") UUID projectId,
            @RequestParam(name = "page") Integer page
    ){
        Pageable pageable = PageRequest.of(page,6).withSort(Sort.by("u.username"));
        List<UserDTO> pagedProjectMember = projectUserService.findPagedProjectMembers(projectId,pageable)
                .stream()
                .map(UserMapper.INSTANCE::mapFromDomainToDto).toList();

        return new ResponseEntity<>(pagedProjectMember,HttpStatus.OK);
    }

    @GetMapping("/{projectId}/task/{taskCode}")
    public ResponseEntity<List<UserDTO>> listUsersAssignedToProjectButNotAssignedToTask(
            @PathVariable("projectId") UUID projectId,
            @PathVariable("taskCode") String taskCode,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "page") Integer page
    ){
        Pageable pageable = PageRequest.of(page,6).withSort(Sort.by("u.username"));
        List<UserDTO> foundUsers = projectTaskService.findUsersInProjectNotAssignedToTaskWithUsername(projectId,taskCode,username, pageable)
                .stream()
                .map(UserMapper.INSTANCE::mapFromDomainToDto)
                .toList();

        return new ResponseEntity<>(foundUsers,HttpStatus.OK);

    }

    @GetMapping("/{projectId}/project-members-size")
    public ResponseEntity<Long> getProjectMembersSize(
            @PathVariable("projectId") UUID projectId
            ){
        Long projectMembersSize = projectUserService.countProjectMembers(projectId);

        return new ResponseEntity<>(projectMembersSize, HttpStatus.OK);
    }
}
