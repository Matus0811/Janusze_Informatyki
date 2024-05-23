package org.project.projectmanagementsystem.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.projectmanagementsystem.api.dto.BugDTO;
import org.project.projectmanagementsystem.api.dto.BugFormDTO;
import org.project.projectmanagementsystem.domain.Bug;
import org.project.projectmanagementsystem.domain.mapper.BugMapper;
import org.project.projectmanagementsystem.services.BugService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/bugs")
@RequiredArgsConstructor
public class BugController {
    private final BugService bugService;

    @PostMapping("/report")
    public ResponseEntity<BugDTO> reportBug(@RequestBody BugFormDTO bugFormDTO) {
        log.info("Processing bug creation with form: [{}]",bugFormDTO);
        Bug createdBug = bugService.createBug(BugMapper.INSTANCE.mapFromDtoToDomain(bugFormDTO));

        BugDTO bugDTO = createBugDto(createdBug);
        return new ResponseEntity<>(bugDTO, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BugDTO>> getPagedBugList(
            @RequestParam(name = "projectId") UUID projectId,
            @RequestParam("page") Integer page
    ) {
        Pageable pageable = PageRequest.of(page,8).withSort(Sort.by("b.reportDate").descending());
        List<BugDTO> bugs = bugService.findBugsForProject(projectId,pageable).stream()
                .map(BugMapper.INSTANCE::mapFromDomainToDto)
                .toList();

        return new ResponseEntity<>(bugs, HttpStatus.OK);
    }

    @GetMapping("/task-bug-list")
    public ResponseEntity<List<BugDTO>> getPagedBugListForTask(
            @RequestParam(name="taskCode") String taskCode,
            @RequestParam(name="page") Integer page
    ){
        log.info("Getting bugs for task: [{}]",taskCode);
        Pageable pageable = PageRequest.of(page,8).withSort(Sort.by("b.reportDate").descending());
        List<BugDTO> bugs = bugService.findBugsForTask(taskCode,pageable).stream()
                .map(BugMapper.INSTANCE::mapFromDomainToDto)
                .toList();

        return new ResponseEntity<>(bugs,HttpStatus.OK);
    }

    @GetMapping("/size")
    public ResponseEntity<Long> getBugListSize(@RequestParam(name = "projectId") UUID projectId) {
        Long reportedBugs = bugService.countProjectReportedBugs(projectId);

        return new ResponseEntity<>(reportedBugs, HttpStatus.OK);
    }

    private BugDTO createBugDto(Bug createdBug) {
        return BugDTO.builder()
                .serialNumber(createdBug.getSerialNumber())
                .title(createdBug.getTitle())
                .description(createdBug.getDescription())
                .task(createdBug.getTaskWithBug())
                .username(createdBug.getReportedUser().getUsername())
                .bugType(createdBug.getBugType())
                .reportDate(createdBug.getReportDate())
                .fixedDate(createdBug.getFixedDate())
                .build();
    }
}
