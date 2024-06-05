package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.projectmanagementsystem.database.entities.BugEntity;
import org.project.projectmanagementsystem.database.jpa.BugJpaRepository;
import org.project.projectmanagementsystem.domain.Bug;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.mapper.BugMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BugRepository {
    private final BugJpaRepository bugJpaRepository;

    public Bug save(Bug bugToCreate) {
        BugEntity entity = BugMapper.INSTANCE.mapFromDomainToEntity(bugToCreate);
        log.info("SAVING: {}",entity.getProject());
        return BugMapper.INSTANCE.mapFromEntityToDomain(
                bugJpaRepository.save(entity)
        );
    }

    public Optional<Bug> findBugForTask(Task task) {
        return bugJpaRepository.findBugWithProjectIdAndTask(task.getProject().getProjectId(),task.getTaskCode())
                .map(BugMapper.INSTANCE::mapFromEntityToDomain);
    }

    public List<Bug> findBugsForProject(UUID projectId, Pageable pageable) {
        return bugJpaRepository.findBugsForProject(projectId,pageable).stream()
                .map(BugMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }

    public Long countProjectReportedBugs(UUID projectId) {
        return bugJpaRepository.countProjectReportedBugs(projectId);
    }

    public List<Bug> findBugsForTask(Task task, Pageable pageable) {
        return bugJpaRepository.findBugsForTask(task.getTaskCode(),pageable)
                .stream()
                .map(BugMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }

    public Optional<Bug> findBugBySerialNumber(String bugSerialNumber) {
        return bugJpaRepository.findBySerialNumber(bugSerialNumber)
                .map(BugMapper.INSTANCE::mapFromEntityToDomain);
    }

    public void removeAll(List<Bug> bugsForTask) {
        bugJpaRepository.deleteAllById(bugsForTask.stream().map(Bug::getBugId).toList());
    }
}
