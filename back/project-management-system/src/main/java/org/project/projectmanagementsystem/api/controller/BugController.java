package org.project.projectmanagementsystem.api.controller;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.api.dto.BugDTO;
import org.project.projectmanagementsystem.api.dto.BugFormDTO;
import org.project.projectmanagementsystem.domain.Bug;
import org.project.projectmanagementsystem.domain.mapper.BugMapper;
import org.project.projectmanagementsystem.services.BugService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bugs")
@RequiredArgsConstructor
public class BugController {
    private final BugService bugService;

    @PostMapping("/report")
    public ResponseEntity<BugDTO> reportBug(@RequestBody BugFormDTO bugFormDTO) {
        Bug createdBug = bugService.createBug(BugMapper.INSTANCE.mapFromDtoToDomain(bugFormDTO));

        BugDTO bugDTO = createBugDto(createdBug);
        return new ResponseEntity<>(bugDTO, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BugDTO>> getBugList(@RequestParam UUID projectId){
        List<BugDTO> bugs = bugService.findBugsForProject(projectId).stream()
                .map(BugMapper.INSTANCE::mapFromDomainToDto)
                .toList();

        return new ResponseEntity<>(bugs,HttpStatus.OK);
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
