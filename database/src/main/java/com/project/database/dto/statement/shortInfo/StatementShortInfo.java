package com.project.database.dto.statement.shortInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementShortInfo {
    private Integer statementNo; // Example: '222222',
    private String tutorFullName; // Example: 'Черкасов Дмитро Іванович',
    private String subjectName; // Example: 'Технології сучасних дата - центрів',
    private String group; // Example: 1,
    private String controlType; // Example: 'екзамен',
    private StudentsCount studentsCount;
    private LocalDate examDate; // Example: '2021-05-24',

}
