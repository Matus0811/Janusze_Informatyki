package org.project.projectmanagementsystem.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="userProjectRoleId")
@Table(name = "user_project_role")
public class UserProjectRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_project_role_id")
    private Long userProjectRoleId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name="project_id")
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name="role_id")
    private RoleEntity role;
}
