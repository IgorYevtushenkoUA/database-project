package com.project.database.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BigunetsMarkId implements Serializable {

    @Column(name = "bigunets_no")
    private Integer bigunetsNo;

    @Column(name = "student_code")
    private Integer studentCode;

    @Column(name = "vidomist_no")
    private Integer vidomistNo;

    @Column(name = "tutor_no")
    private Integer tutorNo;

}
