package com.project.database.dto.statement.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementStudentError {
    private List<String> pibErrorText;
    private List<String> studentRecordBookErrorText;
    private List<String> semesterGradeErrorText;
    private List<String> controlGradeErrorText;
    private List<String> totalGradeErrorText;
    private List<String> nationalGradeErrorText;
    private List<String> ectsGradeErrorText;
}
