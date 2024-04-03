package org.project.projectmanagementsystem.database.entities;

import org.project.projectmanagementsystem.domain.Bug;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "bugId")
@Table(name = "bug")
public class BugEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bug_id")
    private Long bugId;

    @Column(name="serial_number")
    private String serialNumber;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private Bug.BugType bugType;

    @Column(name = "report_date")
    private OffsetDateTime reportDate;

    @Column(name = "fixed")
    private Boolean fixed;
}