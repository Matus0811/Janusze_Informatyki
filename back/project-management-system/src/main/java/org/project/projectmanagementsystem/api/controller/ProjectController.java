package org.project.projectmanagementsystem.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.projectmanagementsystem.api.dto.*;
import org.project.projectmanagementsystem.domain.AssignForm;
import org.project.projectmanagementsystem.domain.RemoveProjectUserForm;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.mapper.FormMapper;
import org.project.projectmanagementsystem.domain.mapper.ProjectMapper;
import org.project.projectmanagementsystem.domain.mapper.UserMapper;
import org.project.projectmanagementsystem.services.ProjectService;
import org.project.projectmanagementsystem.services.UserService;
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
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectFormDTO project) {

        ProjectDTO createdProject = ProjectMapper.INSTANCE
                .mapFromDomainToDto(
                        projectService.processProjectCreation(
                                FormMapper.INSTANCE.mapFromDtoToDomain(project)
                        )
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
    public ResponseEntity<List<ProjectDTO>> findAllOwnerProjects(@RequestParam String email) {
        log.info("Processing searching all projects for owner with email: [{}]", email);

        User owner = userService.findByEmail(email);

        List<ProjectDTO> userProjects = projectService
                .findNotFinishedOwnerProjects(owner)
                .stream()
                .map(ProjectMapper.INSTANCE::mapFromDomainToDto)
                .toList();

        return new ResponseEntity<>(userProjects, HttpStatus.OK);
    }

    @GetMapping("/member-project-list")
    public ResponseEntity<List<ProjectDTO>> findAllUserProjectsAsMember(@RequestParam String email) {
        User user = userService.findByEmail(email);

        List<ProjectDTO> allUserProjectsAsMember = projectService
                .findAllUserProjectsAsMember(user)
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

    @PatchMapping("/{projectId}/add-users")
    public ResponseEntity<?> addUsersToProject(
            @PathVariable("projectId") UUID projectId,
            @RequestBody List<String> emails
    ) {

        List<User> usersToAdd = userService.findUsersByEmail(emails);

        projectService.addUsersToProject(projectId,usersToAdd);
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

        User userToRemove = userService.findByEmail(memberEmail);

        projectService.removeUserFromProject(projectId,userToRemove);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{projectId}/unassigned-users")
    public ResponseEntity<List<UserDTO>> getUnassignedUsers(
            @PathVariable(name = "projectId") UUID projectId
    ) {
        log.info("Searching unassigned users to project: [{}]", projectId);
        List<UserDTO> users = projectService.getUnassignedUsers(projectId).stream()
                .map(UserMapper.INSTANCE::mapFromDomainToDto)
                .toList();

        return ResponseEntity.ok(users);
    }
}
