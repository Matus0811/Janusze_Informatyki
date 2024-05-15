package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.jpa.BugJpaRepository;
import org.project.projectmanagementsystem.domain.Bug;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.mapper.BugMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BugRepository {
    private final BugJpaRepository bugJpaRepository;

    public Bug save(Bug bugToCreate) {
        return BugMapper.INSTANCE.mapFromEntityToDomain(
                bugJpaRepository.save(BugMapper.INSTANCE.mapFromDomainToEntity(bugToCreate))
        );
    }

    public Optional<Bug> findBugForTask(Task task) {
        return bugJpaRepository.findBugWithProjectIdAndTask(task.getProject().getProjectId(),task.getTaskCode())
                .map(BugMapper.INSTANCE::mapFromEntityToDomain);
    }
}
