package com.project.database.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Integer studentCode;
    private String studentSurname;
    private String studentName;
    private String studentPatronymic;
    private String studentRecordBook;

}
