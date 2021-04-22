package com.project.database.dao.inter;

import com.project.database.entity.Subject;

import java.util.List;

public interface SubjectDao {

    List<Subject> findAll(int page, int numberPerPage);

    void deleteByID(int subjectId);

    List<String> findAllSubjectName();

    List<String> findAllSubjectName(String name);

}
