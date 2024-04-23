package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.jpa.BugJpaRepository;
import org.project.projectmanagementsystem.domain.Bug;
import org.project.projectmanagementsystem.domain.mapper.BugMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BugRepository {
    private final BugJpaRepository bugJpaRepository;

    public Bug save(Bug bugToCreate) {
        return BugMapper.INSTANCE.mapFromEntityToDomain(
                bugJpaRepository.save(BugMapper.INSTANCE.mapFromDomainToEntity(bugToCreate))
        );
    }
}
