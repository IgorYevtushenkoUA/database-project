package com.project.database.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_code")
    private Integer studentCode;

    @Column(name = "student_surname")
    private String studentSurname;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "student_patronymic")
    private String studentPatronymic;

    @Column(name = "student_record_book")
    private String studentRecordBook;

}
