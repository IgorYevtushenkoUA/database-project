package com.project.database.entities_;

import com.project.database.entities.StudentEntity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(StudentEntity.class)
public class StudentEntity_ {

    public static volatile SingularAttribute<StudentEntity, Integer> studentCode;
    public static volatile SingularAttribute<StudentEntity, String> studentSurname;
    public static volatile SingularAttribute<StudentEntity, String> studentName;
    public static volatile SingularAttribute<StudentEntity, String> studentPatronymic;
    public static volatile SingularAttribute<StudentEntity, String> studentRecordBook;

}
