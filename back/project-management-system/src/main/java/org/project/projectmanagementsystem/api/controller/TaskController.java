package org.project.projectmanagementsystem.api.controller;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.api.dto.*;
import org.project.projectmanagementsystem.domain.UserTask;
import org.project.projectmanagementsystem.domain.mapper.TaskMapper;
import org.project.projectmanagementsystem.domain.mapper.UserMapper;
import org.project.projectmanagementsystem.services.BugService;
import org.project.projectmanagementsystem.services.ProjectTaskService;
import org.project.projectmanagementsystem.services.TaskService;
import org.project.projectmanagementsystem.services.TaskUserService;
import org.project.projectmanagementsystem.util.TaskUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final ProjectTaskService projectTaskService;
    private final TaskUserService taskUserService;
    private final BugService bugService;


    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskFormDTO taskFormDTO) {
        TaskDTO taskDTO = TaskMapper.INSTANCE.mapFromDomainToDto(
                projectTaskService.processProjectTaskCreation(
                        TaskMapper.INSTANCE.mapFromFormDtoToForm(taskFormDTO)
                )
        );
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @GetMapping("/grouped")
    public ResponseEntity<List<ProjectTaskStatusCount>> getGroupedProjectTaskByStatus(@RequestParam("projectId") UUID projectId){
        List<ProjectTaskStatusCount> projectTaskStatusCountList = taskService.findAllProjectTasksGrouped(projectId);

        return new ResponseEntity<>(projectTaskStatusCountList,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getPagedTasksForProject(
            @RequestParam("projectId") UUID projectId,
            @RequestParam("page") Integer page,
            @RequestParam("taskStatus") String taskStatus
    ) {
        Pageable pageable = PageRequest.of(page, 6).withSort(Sort.by("startDate").descending());
        List<TaskDTO> pagedTasksForProject = taskService.findPagedProjectTasksWithStatus(
                        projectId,
                        TaskUtils.taskStatuses(taskStatus),
                        pageable).stream()
                .map(TaskMapper.INSTANCE::mapFromDomainToDto).toList();

        return new ResponseEntity<>(pagedTasksForProject,HttpStatus.OK);
    }

    @GetMapping("/member-tasks")
    public ResponseEntity<List<TaskDTO>> getPagedMemberTasks(
            @RequestParam("projectId") UUID projectId,
            @RequestParam("page") Integer page,
            @RequestParam("username") String username
    ){
        Pageable pageable = PageRequest.of(page,12).withSort(Sort.by("startDate").descending());
        List<TaskDTO> memberTasks = taskService.findPagedMemberTasks(projectId,username,pageable).stream()
                .map(TaskMapper.INSTANCE::mapFromDomainToDto)
                .toList();

        return new ResponseEntity<>(memberTasks,HttpStatus.OK);
    }

    @PatchMapping("/{taskCode}/add-users")
    public ResponseEntity<?> addUsersToTask(
            @PathVariable("taskCode") String taskCode,
            @RequestBody List<UserDTO> usersToAdd) {

        taskUserService.addUsersToTask(taskCode, usersToAdd.stream().map(UserMapper.INSTANCE::mapFromDtoToDomain).toList());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{taskCode}/users")
    public ResponseEntity<List<UserDTO>> getPagedUsersAssignedToTask(
            @PathVariable("taskCode") String taskCode,
            @RequestParam("page") Integer page
    ){
        Pageable pageable = PageRequest.of(page,6).withSort(Sort.by("u.username"));

        List<UserDTO> pagedUsersAssignedToTask = taskUserService.findPagedUsersAssignedToTask(taskCode,pageable)
                .stream()
                .map(UserTask::getUser)
                .map(UserMapper.INSTANCE::mapFromDomainToDto)
                .toList();

        return new ResponseEntity<>(pagedUsersAssignedToTask,HttpStatus.OK);
    }

    @PutMapping("/{taskCode}/users/finish-task")
    public ResponseEntity<?> finishTaskByMember(
            @PathVariable("taskCode") String taskCode,
            @RequestParam("email") String email,
            @RequestParam("projectId") UUID projectId
    ) {
        taskUserService.finishTaskByMember(taskCode, email, projectId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{taskCode}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable("taskCode") String taskCode) {
        bugService.removeBugsAssignedToTask(taskCode);
        taskService.deleteTask(taskCode);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{taskCode}/remove-user")
    public ResponseEntity<?> removeUserFromTask(
            @PathVariable("taskCode") String taskCode,
            @RequestParam("username") String username
            ){
        taskUserService.removeUserAssignedToTask(taskCode,username);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
