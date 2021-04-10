package com.project.database.dto.statement.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementFooter {
    private Integer presentCount; // Example: 6,
    private Integer absentCount; // Example: 0,
    private Integer rejectedCount; // Example: 0
}
