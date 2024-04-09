package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.jpa.RoleJpaRepository;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.domain.mapper.RoleMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepository {
    private final RoleJpaRepository roleJpaRepository;

    public Optional<Role> findByName(String roleName) {
        return roleJpaRepository.findByName(roleName).map(RoleMapper.INSTANCE::mapFromEntityToDomain);
    }
}
