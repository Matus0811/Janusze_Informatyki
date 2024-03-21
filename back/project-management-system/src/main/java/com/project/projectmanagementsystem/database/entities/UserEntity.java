package com.project.projectmanagementsystem.database.entities;

import com.project.projectmanagementsystem.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"userId","username","email"})
@Table(name = "user_table")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name="user_name", unique = true)
    @NotNull
    private String username;

    @Column(name="password")
    @NotNull
    private String password;

    @Column(name="name")
    @NotNull
    private String name;

    @Column(name="surname")
    @NotNull
    private String surname;

    @Column(name="gender")
    private User.Gender gender;

    @Column(name="email")
    @NotNull
    private String email;

    @Column(name="phone")
    @NotNull
    private String phone;

    @OneToMany(mappedBy = "user")
    private Set<UserProjectRoleEntity> projectRoleEntities;

    @OneToMany(mappedBy = "user")
    private Set<CommentEntity> commentEntities;

    @OneToMany(mappedBy = "user")
    private Set<UserTaskEntity> userTaskEntities;

    @OneToMany(mappedBy = "user")
    private Set<BugEntity> reportedBugs;
}
