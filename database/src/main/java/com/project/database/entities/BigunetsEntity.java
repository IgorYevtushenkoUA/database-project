package com.project.database.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "bigunets")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BigunetsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bigunets_no")
    private Integer bigunetsNo;

    @Column(name = "exam_date")
    private LocalDate examDate;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    @Column(name = "postp_reason")
    private String postpReason;

    @Column(name = "control_type")
    private String controlType;

    @ManyToOne
    @JoinColumn(name = "tutor_code")
    private TutorEntity tutor;

}
