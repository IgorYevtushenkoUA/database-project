package com.project.database.dao.inter;

import com.project.database.entity.VidomistEntity;

public interface VidomistDao {

//    отримати відомість для заданого викладача/предмета/групи

    /**
     * отримати відомість для заданого викладача
     */
    public VidomistEntity findVidomistByTutor(String tutorName);

    /**
     * отримати відомість для заданого викладача
     */
    public VidomistEntity findVidomistByTutor(int tutorNo);

    /**
     * отримати відомість для заданого предмета
     */
    public VidomistEntity findVidomistBySubject(String subjectName);

    /**
     * отримати відомість для заданого предмета
     */
    public VidomistEntity findVidomistBySubject(int subjectNo);

    /**
     * отримати відомість для заданої групи
     */
    public VidomistEntity findVidomistByGroup(String groupName);

    /**
     * отримати відомість для заданої групи
     */
    public VidomistEntity findVidomistByGroup(int groupCode);

}
