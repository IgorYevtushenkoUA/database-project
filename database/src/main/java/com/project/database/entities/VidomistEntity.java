package com.project.database.entities;

import lombok.*;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vidomist_no")
    private Integer vidomistNo;

    @ManyToOne
    @JoinColumn(name = "tutor_no")
    private TutorEntity tutor;

    @Column(name = "present_count")
    private int presentCount;

    @Column(name = "adsent_count")
    private int absentCount;

    @Column(name = "rejected_count")
    private int rejectedCount;

    @Column(name = "control_type")
    private String controlType;

    @Column(name = "exam_date")
    private Date examDate;

    @ManyToOne
    @JoinColumn(name = "group_code")
    private GroupEntity group;



}

