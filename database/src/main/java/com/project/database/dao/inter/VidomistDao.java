package com.project.database.dao.inter;

import com.project.database.entity.Vidomist;

import java.util.List;

public interface VidomistDao {

//    отримати відомість для заданого викладача/предмета/групи

    /**
     * отримати відомість для заданого викладача\групи\предмету
     */
    public List<Vidomist> findAllVidomistsByTutorGroupSubject(String tutorName, String groupName, String subjectName, int page, int numberPerPage);

}
