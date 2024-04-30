package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.jpa.BugJpaRepository;
import org.project.projectmanagementsystem.domain.Bug;
import org.project.projectmanagementsystem.domain.mapper.BugMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BugRepository {
    private final BugJpaRepository bugJpaRepository;

    public Bug save(Bug bugToCreate) {
        return BugMapper.INSTANCE.mapFromEntityToDomain(
                bugJpaRepository.save(BugMapper.INSTANCE.mapFromDomainToEntity(bugToCreate))
        );
    }

    public Optional<Bug> findBugWithProjectId(UUID projectId) {
        return bugJpaRepository.findBugWithProjectId(projectId)
                .map(BugMapper.INSTANCE::mapFromEntityToDomain);
    }
}
