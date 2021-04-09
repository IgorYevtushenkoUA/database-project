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
public class StatementHeaderErrors {
    private List<String> eduLevelErrorText;
    private List<String> facultyErrorText;
    private List<String> courseErrorText;
    private List<String> groupErrorText;
    private List<String> subjectNameErrorText;
    private List<String> semesterErrorText;
    private List<String> creditNumberErrorText;
    private List<String> controlTypeErrorText;
    private List<String> examDateErrorText;
    private List<String> tutorFullNameErrorText;
    private List<String> tutorPositionErrorText;
    private List<String> tutorAcademicStatusErrorText;
}
