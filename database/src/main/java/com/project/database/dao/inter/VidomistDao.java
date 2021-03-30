package com.project.database.dao.inter;

import com.project.database.entity.Vidomist;

public interface VidomistDao {

//    отримати відомість для заданого викладача/предмета/групи

    /**
     * отримати відомість для заданого викладача
     */
    public Vidomist findVidomistByTutor(String tutorName);

    /**
     * отримати відомість для заданого викладача
     */
    public Vidomist findVidomistByTutor(int tutorNo);

    /**
     * отримати відомість для заданого предмета
     */
    public Vidomist findVidomistBySubject(String subjectName);

    /**
     * отримати відомість для заданого предмета
     */
    public Vidomist findVidomistBySubject(int subjectNo);

    /**
     * отримати відомість для заданої групи
     */
    public Vidomist findVidomistByGroup(String groupName);

    /**
     * отримати відомість для заданої групи
     */
    public Vidomist findVidomistByGroup(int groupCode);

}
