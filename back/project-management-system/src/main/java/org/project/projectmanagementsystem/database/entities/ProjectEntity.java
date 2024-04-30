package org.project.projectmanagementsystem.database.entities;

import org.hibernate.annotations.Fetch;
import org.project.projectmanagementsystem.domain.Project;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="projectId")
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Project.ProjectStatus projectStatus;

    @Column(name="start_date")
    private OffsetDateTime startDate;

    @Column(name="finish_date")
    private OffsetDateTime finishDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private Set<UserProjectRoleEntity> userProjectRoleEntities;

    @OneToMany(mappedBy = "project",cascade = CascadeType.REMOVE)
    private Set<TaskEntity> taskEntities;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private Set<BugEntity> bugEntities;
}
