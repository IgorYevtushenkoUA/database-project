package com.project.database.dao.inter;

import com.project.database.entity.Subject;

import java.util.TreeMap;

public interface CalculationsDao {
/*
    - отримати кількість (не)допусків для групи/предмета/у викладача
    - вирахувати середній бал для group\курсу
    - вирахувати середній бал для студентів по предметах
*/

    /**
     * - отримати кількість (не)допусків для групи/предмета/у викладача
     */
    public int getNonAdmissionForGroupSubjectInTutor(
            String groupName,
            String subjectName,
            String tutorName
    );

    /**
     * - отримати кількість (не)допусків для групи/предмета/у викладача
     */
    public int getNonAdmissionForGroupSubjectInTutor(
            int groupCode,
            int subjectNo,
            int tutorNo
    );


    /**
     * - отримати кількість допусків для групи/предмета/у викладача
     */
    public int getAdmissionForGroupSubjectInTutor(
            String groupName,
            String subjectName,
            String tutorName
    );

    /**
     * - отримати кількість допусків для групи/предмета/у викладача
     */
    public int getAdmissionForGroupSubjectInTutor(
            int groupCode,
            int subjectNo,
            int tutorNo
    );

    /**
     * - вирахувати середній бал для group\курсу
     */
    public double getAverageMarkForGroupByCourse(
            String groupName,
            String subjectName
    );

    /**
     * - вирахувати середній бал для group\курсу
     */
    public double getAverageMarkForGroupByCourse(
            int groupCode,
            int subjectNo
    );

    /** - вирахувати середній бал для студентів по предметах*/
    public TreeMap<Subject, Double> getAverageMArsBySubjects();

}
