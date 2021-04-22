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
public class StatementFooterErrors {
    private List<String> presentCountErrorText;
    private List<String> absentCountErrorText;
    private List<String> rejectedCountErrorText;
}
