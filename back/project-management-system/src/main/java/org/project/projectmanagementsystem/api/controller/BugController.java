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

    //TODO add getMapping
    private BugDTO createBugDto(Bug createdBug) {
        return BugDTO.builder()
                .serialNumber(createdBug.getSerialNumber())
                .title(createdBug.getTitle())
                .description(createdBug.getDescription())
                .project(createdBug.getProject().getProjectId())
                .username(createdBug.getUser().getUsername())
                .bugType(createdBug.getBugType())
                .reportDate(createdBug.getReportDate())
                .fixedDate(createdBug.getFixedDate())
                .build();
    }
}
