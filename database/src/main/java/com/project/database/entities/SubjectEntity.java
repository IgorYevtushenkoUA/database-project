package com.project.database.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "subject")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_no")
    private Integer subjectNo;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "edu_level")
    private String eduLevel;

    @Column(name = "faculty")
    private String faculty;

}
