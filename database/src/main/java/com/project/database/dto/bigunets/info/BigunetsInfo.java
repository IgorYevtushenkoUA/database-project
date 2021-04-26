package com.project.database.dto.bigunets.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BigunetsInfo {
    private BigunetsHeader bigunetsHeader;
    private List<BigunetsStudent> bigunetsStudents;
}

