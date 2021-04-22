package com.project.database.dto.statement.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementErrors {
    private StatementHeaderErrors headerErrors;
    private StatementFooterErrors footerErrors;
    private StatementStudentErrorsMap studentErrorsMap;
}
