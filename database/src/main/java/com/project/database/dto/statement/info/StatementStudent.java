package com.project.database.dto.statement.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementStudent {
    private Integer studentId; // Example: 23,
    private String studentPI; // Example: "Бойчук Олег",
    private String studentPatronymic; // Example: "Романович",
    private String studentRecordBook; // Example: "І 303/10 бп",
    private Integer semesterGrade; // Example: 60,
    private Integer controlGrade; // Example: 30,
    private Integer totalGrade; // Example: null,
    private String nationalGrade; // Example: "Добре",
    private String ectsGrade; // Example: 'B'
}
