package com.project.database.entities;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class StatementHeaderEntity {
    private Integer statementNo; // Example: '222222',
    private String eduLevel; // Example: 'Бакалавр',
    private String faculty; // Example: 'Факультет інформатики',
    private Integer course; // Example: 3,
    private String groupName; // Example: 1,
    private String subjectName; // Example: 'Технології сучасних дата - центрів',
    private String trim; // Example: '6д',
    private String controlType; // Example: 'екзамен',
    private LocalDate examDate; // Example: '2021-05-24',
    private String tutorSurname; // Example: 'Черкасов Дмитро Іванович',
    private String tutorName; // Example: 'Черкасов Дмитро Іванович',
    private String tutorPatronymic; // Example: 'Черкасов Дмитро Іванович',
    private String tutorPosition; // Example: 'старший викладач',
    private String tutorAcademicStatus; // Example: 'кандидат технічних наук'
}
