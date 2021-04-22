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
public class VidomistMarkId implements Serializable {

    @Column(name = "vidomist_no")
    private Integer vidomistNo;

    @Column(name = "student_code")
    private Integer studentCode;

}
