package com.project.database.dto.bigunets.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BigunetsErrors {
    private BigunetsHeaderErrors headerErrors;
    private BigunetsStudentErrorsMap studentErrorsMap;
}
