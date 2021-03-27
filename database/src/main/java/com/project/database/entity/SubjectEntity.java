package com.project.database.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SubjectEntity {

    private Integer subjectNo;
    private String subjectName;
    private String eduLevel;
    private String faculty;

}
