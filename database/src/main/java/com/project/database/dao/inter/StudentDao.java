package com.project.database.dao.inter;

import com.project.database.entity.StudentEntity;
import com.project.database.entity.SubjectEntity;
import com.project.database.entity.VidomistEntity;

import java.util.List;
import java.util.TreeMap;

public interface StudentDao {

    /*
    - отримати всіх боржників за роком навчання / спеціальністю / предметом / групою / викладачем
    - отримати всіх студентів за роком навчання / спеціальністю  / предметом / групою / викладачем
    - отримати n% перших студентів за рейтингом (на спеціальності за семестр)
    - генерувати список студентів за рейтингом для спеціальності\курсу
    - отримати остаточну оцінку/ бігунці / середній бал/ всі оцінки студента за ПІБ
    * */

    /**
     * Знайти всіх Студентів
     */
    List<StudentEntity> findAllStudent();

    /**
     * Знайти студентів за (ім'я + прізвище + по-батькові)
     */
    List<StudentEntity> findStudentsByPIB(String name,
                                          String surname,
                                          String patronymic);

    /**
     * Знайти студентів за (ім'я + прізвище + по-батькові)
     */
    List<StudentEntity> findStudentsByNameSurname(String name,
                                                  String surname);

    /**
     * - отримати всіх боржників за роком навчання / предметом / групою / викладачем
     */
    List<StudentEntity> findAllDebtorsByYearSubjectGroupTeacher(
            String eduYear,
            String subjectName,
            String groupName,
            String tutorName
    );

    /**
     * - отримати всіх боржників за роком навчання / предметом / групою / викладачем
     */
    List<StudentEntity> findAllDebtorsByYearSubjectGroupTeacher(
            String eduYear,
            int subjectNo,
            int groupCode,
            int tutorNo
    );

    /**
     * - отримати всіх студентів за роком навчання/ предметом / групою / викладачем
     */
    List<StudentEntity> findAllStudentsByYearSubjectGroupTeacher(
            String eduYear,
            String subject,
            String groupName,
            String tutorName
    );

    /**
     * - отримати всіх студентів за роком навчання/ предметом / групою / викладачем
     */
    List<StudentEntity> findAllStudentsByYearSubjectGroupTeacher(
            String eduYear,
            int subjectNo,
            int groupCode,
            int tutorNo
    );


    /**
     * -  отримати n% перших студентів за рейтингом (на спеціальності за семестр)    НАЙКРАЩИХ
     */
    List<StudentEntity> findBestPercentOfStudentByGroupCourse(
            int percent,
            int groupCode,
            int course
    );

    /**
     * -  отримати n% перших студентів за рейтингом (на спеціальності за семестр)   НАЙКРАЩИХ
     */
    List<StudentEntity> findBestPercentOfStudentByGroupCourse(
            int percent,
            String groupName,
            int course
    );

    /**
     * -  отримати n% перших студентів за рейтингом (на спеціальності за семестр)     ГІРШИХ
     */
    List<StudentEntity> findWorstPercentOfStudentByGroupCourse(
            int percent,
            int groupCode,
            int course
    );

    /**
     * -  отримати n% перших студентів за рейтингом (на спеціальності за семестр)     ГІРШИХ
     */

    List<StudentEntity> findWorstPercentOfStudentByGroupCourse(
            int percent,
            String groupName,
            int course
    );

    /**
     * - генерувати список студентів за рейтингом для group\курсу
     */

    List<StudentEntity> sortAllStudentInGroupCourseByFromHighToLowRating(
            int percent,
            String groupName,
            int course
    );


    /**
     * - генерувати список студентів за рейтингом для group\курсу
     */

    List<StudentEntity> sortAllStudentInGroupCourseByFromHighToLowRating(
            int percent,
            int groupCode,
            int course
    );

    /**
     * - генерувати список студентів за рейтингом для group\курсу
     */

    List<StudentEntity> sortAllStudentInGroupCourseByFromLowToHighRating(
            int percent,
            String groupName,
            int course
    );


    /**
     * - генерувати список студентів за рейтингом для group\курсу
     */

    List<StudentEntity> sortAllStudentInGroupCourseByFromLowToHighRating(
            int percent,
            int groupCode,
            int course
    );

/**
 - отримати остаточну оцінку/ бігунці / середній бал/ всі оцінки студента за ПІБ
 * */

    /** - отримати бігунці студента за ПІБ*/
    public List<VidomistEntity> findAllVidomostyByStudentId(int studentId);

    /** - отримати бігунці студента за ПІБ*/
    public List<VidomistEntity> findAllVidomostyByStudentPIB(String name,
                                                             String surname,
                                                             String patronymic);

    /** - отримати середній бал студента за ПІБ*/
    public double findAverageMarkForStudentById(int studentCode);

    /** - отримати середній бал студента за ПІБ*/
    public double findAverageMarkForStudentByPIB(String name,
                                                 String surname,
                                                 String patronymic);

    /** - отримати всі оцінки студента за ПІБ*/
    public TreeMap<SubjectEntity, Integer> findAllMArksForStudentByPIB(String name,
                                                                       String surname,
                                                                       String patronymic);

    /** - отримати всі оцінки студента за ПІБ*/
    public TreeMap<SubjectEntity, Integer> findAllMArksForStudentById(int studentCode);

    /** - отримати остаточну оцінку/ бігунці / середній бал/ всі оцінки студента за ПІБ*/

}
