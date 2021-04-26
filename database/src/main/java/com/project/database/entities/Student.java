package com.project.database.entities;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private long studentCode;
    private String studentSurname;
    private String studentName;
    private String studentPatronymic;
    private String studentRecordBook;

}
