package com.project.database.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tutor")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tutor_no")
    private Integer tutorNo;

    @Column(name = "tutor_name")
    private String tutorName;

    @Column(name = "tutor_surname")
    private String tutorSurname;

    @Column(name = "tutor_patronymic")
    private String tutorPatronymic;

    @Column(name = "science_degree")
    private String scienceDegree;

    @Column(name = "academ_status")
    private String academStatus;

    @Column(name = "position")
    private String position;

}
