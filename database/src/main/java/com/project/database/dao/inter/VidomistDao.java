package com.project.database.dao.inter;

import com.project.database.entity.Vidomist;
import com.project.database.service.VidomistService;

import java.util.List;

public interface VidomistDao {

//    отримати відомість для заданого викладача/предмета/групи

    /**
     * отримати відомість для заданого викладача\групи\предмету
     */
    List<Vidomist> findAllByTutorGroupSubject(String tutorName, String groupName, String subjectName, int page, int numberPerPage);

    /**
     * delete vidomist by ID
     */
    void deleteById(int vidomistId);

    /**
     * find all vidomisty
     */
    List<Vidomist> findAll(int page, int numberPerPage);

    Vidomist findById(int id);


}
