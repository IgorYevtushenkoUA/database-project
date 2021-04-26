package com.project.database.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "vidomist")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VidomistEntity {

    @Id
    @Column(name = "vidomist_no")
    private Integer vidomistNo;

    @ManyToOne
    @JoinColumn(name = "tutor_no")
    private TutorEntity tutor;

    @Column(name = "present_count")
    private int presentCount;

    @Column(name = "absent_count")
    private int absentCount;

    @Column(name = "rejected_count")
    private int rejectedCount;

    @Column(name = "control_type")
    private String controlType;

    @Column(name = "exam_date")
    private LocalDate examDate;

    @ManyToOne
    @JoinColumn(name = "group_code")
    private GroupEntity group;



}

