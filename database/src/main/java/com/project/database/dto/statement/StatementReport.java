package com.project.database.dto.statement;

import com.project.database.dto.statement.errors.StatementErrors;
import com.project.database.dto.statement.info.StatementInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementReport {
    private StatementInfo statementInfo;
    private StatementErrors statementErrors;
}
