package com.project.database.dto.bigunets.shortInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BigunetsShortInfo {
    private Integer statementNo; // Example: '222222',
    private String tutorFullName; // Example: 'Черкасов Дмитро Іванович',
    private String subjectName; // Example: 'Технології сучасних дата - центрів',
    private String controlType; // Example: 'екзамен',
    private String postponeReason; // Example: 1,
    private LocalDate examDate; // Example: '2021-05-24',
    private LocalDate validUntil; // Example: '2021-05-26',
}
