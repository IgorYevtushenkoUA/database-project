package com.project.database.dto.statement.shortInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentsCount {
    private Integer presentCount; // Example: 30,
    private Integer absentCount; // Example: 2,
    private Integer rejectedCount; // Example: 0,
}
