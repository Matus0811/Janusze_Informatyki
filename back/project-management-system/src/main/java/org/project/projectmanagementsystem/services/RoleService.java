package org.project.projectmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.RoleRepository;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.services.exceptions.RoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findRoleByName(String roleName){
        return roleRepository.findByName(roleName).orElseThrow(
                () -> new RoleNotFoundException("Given role: [%s] not found".formatted(roleName), HttpStatus.NOT_FOUND)
        );
    }
}
