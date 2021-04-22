package com.project.database.dto.bigunets.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BigunetsHeader {
    private Integer bigunNo; // Example: '222222',
    private String eduLevel; // Example: 'Бакалавр',
    private String faculty; // Example: 'Факультет інформатики',
    private Integer course; // Example: 3,
    private String group; // Example: бігунець,
    private String subjectName; // Example: 'Технології сучасних дата - центрів',
    private String semester; // Example: '6д',
    private String creditNumber; // Example: '?',
    private LocalDate dueTo; // Example: '2021-05-24'
    private String postponeReason; // Example: 'академічна заборгованість'
    private String controlType; // Example: 'екзамен',
    private LocalDate examDate; // Example: '2021-05-24',
    private String tutorFullName; // Example: 'Черкасов Дмитро Іванович',
    private String tutorPosition; // Example: 'старший викладач',
    private String tutorAcademicStatus; // Example: 'кандидат технічних наук'
}
