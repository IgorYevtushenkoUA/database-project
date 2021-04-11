package com.project.database.dto.bigunets.errors;

import com.project.database.dto.bigunets.info.BigunetsStudent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BigunetsStudentErrorsMap {

    /**
     * Errors' text for the each student in {@link BigunetsStudent}
     * <ul>
     *     <li>key: Student's surname and name</li>
     *     <li>value: {@link BigunetsStudentError}</li>
     * </ul>
     */
    private HashMap<String, BigunetsStudentError> bigunStudentErrorsMap;
}
