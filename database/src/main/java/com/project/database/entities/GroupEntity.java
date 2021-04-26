package com.project.database.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "\"group\"")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_code")
    private Integer groupCode;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "edu_year")
    private String eduYear;

    @Column(name = "trim")
    private String trim;

    @Column(name = "course")
    private Integer course;

    @ManyToOne
    @JoinColumn(name = "subject_no")
    private SubjectEntity subject;


}
