package org.project.projectmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.dao.RoleDAO;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.services.exceptions.RoleNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleDAO roleDAO;

    public Role findRoleByName(String roleName){
        return roleDAO.findByName(roleName).orElseThrow(
                () -> new RoleNotFoundException("Given role: [%s] not found".formatted(roleName))
        );
    }
}
