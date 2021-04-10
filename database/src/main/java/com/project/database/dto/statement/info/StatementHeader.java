package com.project.database.dto.statement.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementHeader {
    private Integer statementNo; // Example: '222222',
    private String eduLevel; // Example: 'Бакалавр',
    private String faculty; // Example: 'Факультет інформатики',
    private Integer course; // Example: 3,
    private Integer group; // Example: 1,
    private String subjectName; // Example: 'Технології сучасних дата - центрів',
    private String semester; // Example: '6д',
    private String creditNumber; // Example: '?',
    private String controlType; // Example: 'екзамен',
    private LocalDate examDate; // Example: '2021-05-24',
    private String tutorFullName; // Example: 'Черкасов Дмитро Іванович',
    private String tutorPosition; // Example: 'старший викладач',
    private String tutorAcademicStatus; // Example: 'кандидат технічних наук'
}
