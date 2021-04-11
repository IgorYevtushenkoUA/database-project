package com.project.database.entities_;

import com.project.database.entities.SubjectEntity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SubjectEntity.class)
public class SubjectEntity_ {

    public static volatile SingularAttribute<SubjectEntity, Integer> subjectNo;
    public static volatile SingularAttribute<SubjectEntity, String> subjectName;
    public static volatile SingularAttribute<SubjectEntity, String> eduLevel;
    public static volatile SingularAttribute<SubjectEntity, String> faculty;

}
