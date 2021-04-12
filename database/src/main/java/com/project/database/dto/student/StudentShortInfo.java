package com.project.database.dto.student;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class StudentShortInfo {
    private Integer studentId; // Example: 4,
    private String studentSurname; // Example: "Бойко",
    private String studentName; // Example: "Данило",
    private String studentPatronymic; // Example: "Романович",
    private String studentRecordBook; // Example: "37453",
    private Double studentRating; // Example: 76.3,
    private Integer studentCourse; // Example: 2,
    private String studentTrim; // Example: 2,
//    private String faculty; // Example: "Факультет інформатики"
}
