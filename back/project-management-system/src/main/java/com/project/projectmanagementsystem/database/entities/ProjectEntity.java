package com.project.projectmanagementsystem.database.entities;

import com.project.projectmanagementsystem.domain.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    @OneToMany(mappedBy = "project")
    private Set<UserProjectRoleEntity> userProjectRoleEntities;

    @OneToMany(mappedBy = "project")
    private Set<TaskEntity> taskEntities;

    @OneToMany(mappedBy = "project")
    private Set<BugEntity> bugEntities;
}
