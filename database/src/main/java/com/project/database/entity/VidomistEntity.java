package com.project.database.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VidomistEntity {

    private Integer vidomistNo;
    private Integer tutorNo;
    private Integer presentCount;
    private Integer absentCount;
    private Integer rejectedCount;
    private String controlType;
    private Date examDate;
    private Integer groupCode;

}
