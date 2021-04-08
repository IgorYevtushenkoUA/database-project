package com.project.database.entities_;

import com.project.database.entities.GroupEntity;
import com.project.database.entities.SubjectEntity;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(GroupEntity.class)
public class GroupEntity_ {

    public static volatile SingularAttribute<GroupEntity, Integer> groupCode;
    public static volatile SingularAttribute<GroupEntity, String> groupName;
    public static volatile SingularAttribute<GroupEntity, String> eduYear;
    public static volatile SingularAttribute<GroupEntity, String> trim;
    public static volatile SingularAttribute<GroupEntity, Integer> course;
    public static volatile SetAttribute<GroupEntity,SubjectEntity> subject;

}
