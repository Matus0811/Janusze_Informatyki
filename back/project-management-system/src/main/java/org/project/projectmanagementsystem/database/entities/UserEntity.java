package org.project.projectmanagementsystem.database.entities;

import org.project.projectmanagementsystem.domain.User;
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

    @Column(name="email", unique = true)
    @NotNull
    private String email;

    @Column(name="phone")
    @NotNull
    private String phone;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private Set<UserProjectRoleEntity> projectRoleEntities;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private Set<CommentEntity> commentEntities;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private Set<UserTaskEntity> userTaskEntities;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private Set<BugEntity> reportedBugs;
}

