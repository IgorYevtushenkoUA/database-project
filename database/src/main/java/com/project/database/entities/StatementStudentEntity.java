package com.project.database.entities;

import com.project.database.dto.statement.info.StatementFooter;
import com.project.database.dto.statement.info.StatementHeader;
import com.project.database.dto.statement.info.StatementStudent;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class StatementStudentEntity {
    private Integer studentId; // Example: 23,
    private String studentSurname; // Example: "Бойчук Олег",
    private String studentName; // Example: "Бойчук Олег",
    private String studentPatronymic; // Example: "Романович",
    private String studentRecordBook; // Example: "І 303/10 бп",
    private Integer semesterGrade; // Example: 60,
    private Integer controlGrade; // Example: 30,
    private Integer totalGrade; // Example: null,
    private String nationalGrade; // Example: "Добре",
    private String ectsGrade; // Example: 'B'
}
