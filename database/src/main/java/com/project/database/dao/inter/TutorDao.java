package com.project.database.dao.inter;

import com.project.database.entity.Tutor;

import java.util.List;

public interface TutorDao {
    void deleteByID(int tutorId);

    List<Tutor> findAll(int page, int numberPerPage);
}
