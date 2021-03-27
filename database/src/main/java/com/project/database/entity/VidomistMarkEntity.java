package com.project.database.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VidomistMarkEntity {

    private Integer vidomistNo;
    private Integer studentCode;
    private Integer trimMark;
    private String natMark;
    private Integer markCheck;
    private Integer completeMark;
    private String ectsMark;

}
