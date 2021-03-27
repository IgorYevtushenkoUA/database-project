package com.project.database.entity;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Bigunets {

    private Integer bigunetsNo;
    private Date examDate;
    private Date validUntil;
    private String postpReason;
    private String controlType;
    private int tutorCode;

}
