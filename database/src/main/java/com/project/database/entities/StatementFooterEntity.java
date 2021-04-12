package com.project.database.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class StatementFooterEntity {

    private Integer presentCount; // Example: 6,
    private Integer absentCount; // Example: 0,
    private Integer rejectedCount; // Example: 0

}
