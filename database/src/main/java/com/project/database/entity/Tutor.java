package com.project.database.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Tutor {

    private Integer tutorNo;
    private String tutorName;
    private String tutorSurname;
    private String tutorPatronymic;
    private String scienceDegree;
    private String academStatus;
    private String position;

}
