package org.project.projectmanagementsystem.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="roleId")
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long roleId;

    @Column(name="name",unique = true)
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<UserProjectRoleEntity> userProjectRoleEntities;
}
