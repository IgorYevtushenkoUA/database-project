package com.project.database.dto.subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentSubjectShortInfo {
    private Integer subjectID; // Example: 1,
    private String subjectName; // Example: 'Технології сучасних дата - центрів',
    private String tutorFullName; // Example: "Черкасов Дмитро Іванович",
    private String group; // Example: "1",
    private String controlType; // Example: "екзамен",
    private String examDate; // Example: "2021-05-25",
    private Integer grade; // Example: 90
    private Integer studentCode;
}
