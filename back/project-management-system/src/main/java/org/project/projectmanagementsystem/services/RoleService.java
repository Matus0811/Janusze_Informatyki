package org.project.projectmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.RoleRepository;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.services.exceptions.RoleNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findRoleByName(String roleName){
        return roleRepository.findByName(roleName).orElseThrow(
                () -> new RoleNotFoundException("Given role: [%s] not found".formatted(roleName))
        );
    }
}
