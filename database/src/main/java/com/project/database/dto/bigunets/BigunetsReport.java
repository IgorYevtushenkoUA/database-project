package com.project.database.dto.bigunets;

import com.project.database.dto.bigunets.errors.BigunetsErrors;
import com.project.database.dto.bigunets.info.BigunetsInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BigunetsReport {
    private BigunetsInfo bigunetsInfo;
    private BigunetsErrors bigunetsErrors;
}
