package com.project.database.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    private Integer groupCode;
    private String groupName;
    private String eduYear;
    private String trim;
    private Integer course;
    private Integer subjectNo;

}
