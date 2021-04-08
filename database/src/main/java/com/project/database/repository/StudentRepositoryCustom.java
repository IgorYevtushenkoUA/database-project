package com.project.database.repository;

import com.project.database.entities.GroupEntity;

import java.util.List;

public interface StudentRepositoryCustom {
    List<String> findTrims(String trim);

    List<GroupEntity> findAverageStudentsMarksTrimCourse();
}
