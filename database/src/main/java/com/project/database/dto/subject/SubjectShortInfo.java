package com.project.database.dto.subject;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectShortInfo {
    private Integer subjectID; // Example: 1,
    private String subjectName; // Example: 'Технології сучасних дата - центрів',
    private String tutorFullName; // Example: "Черкасов Дмитро Іванович",
    private BigDecimal grade; // Example: 90
}