package com.project.database.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "vidomist_mark")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VidomistMarkEntity {

    @EmbeddedId
    private VidomistMarkId vidomistMarkId;

    @Column(name = "trim_mark")
    private int trimMark;

    @Column(name = "nat_mark")
    private String natMark;

    @Column(name = "mark_check")
    private int markCheck;

    @Column(name = "complete_mark")
    private int completeMark;

    @Column(name = "ects_mark")
    private String ectsMark;

}


