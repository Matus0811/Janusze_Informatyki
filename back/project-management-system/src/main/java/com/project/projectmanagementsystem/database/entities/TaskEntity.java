package com.project.projectmanagementsystem.database.entities;


import com.project.projectmanagementsystem.domain.Task;
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
@EqualsAndHashCode(of="taskId")
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private Long taskId;

    @Column(name="task_code", unique = true)
    private String taskCode;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Task.TaskStatus status;

    @Column(name="priority")
    @Enumerated(EnumType.STRING)
    private Task.Priority priority;

    @Column(name="start_date")
    private OffsetDateTime startDate;

    @Column(name="finish_date")
    private OffsetDateTime finishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @OneToMany(mappedBy = "task")
    private Set<CommentEntity> commentEntities;

    @OneToMany(mappedBy = "task")
    private Set<UserTaskEntity> userTaskEntities;
}

