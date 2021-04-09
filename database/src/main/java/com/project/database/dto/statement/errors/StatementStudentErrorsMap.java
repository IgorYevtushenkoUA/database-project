package com.project.database.dto.statement.errors;

import com.project.database.dto.statement.info.StatementStudent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementStudentErrorsMap {

    /**
     * Errors' text for the each student in {@link StatementStudent}
     * <ul>
     *     <li>key: Student's record book no</li>
     *     <li>value: {@link StatementStudentError}</li>
     * </ul>
     */
    private HashMap<String, List<StatementStudentError>> statementStudentsErrorsMap;
}
