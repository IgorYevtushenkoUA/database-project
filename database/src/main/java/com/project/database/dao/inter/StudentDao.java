package com.project.database.dao.inter;

import com.project.database.entity.Student;
import com.project.database.entity.Subject;
import com.project.database.entity.Vidomist;

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
    public List<Student> findAllStudents(int page, int numberPerPage);

    /**
     * Знайти студентів за (ім'я + прізвище + по-батькові)
     */
    List<Student> findStudentsByPIB(String name,
                                    String surname,
                                    String patronymic,
                                    int page,
                                    int numberPerPage);

    /**
     * Знайти студентів за (ім'я + прізвище + по-батькові)
     */
    List<Student> findStudentsByNameSurname(String name,
                                            String surname,
                                            int page,
                                            int numberPerPage);

    /**
     * - отримати всіх боржників за роком навчання / предметом / групою / викладачем
     */
    List<Student> findAllDebtorsByYearSubjectGroupTeacher(
            String eduYear,
            String subjectName,
            String groupName,
            String tutorName,
            int page,
            int numberPerPage);

    /**
     * - отримати всіх боржників за роком навчання / предметом / групою / викладачем
     */
    List<Student> findAllDebtorsByYearSubjectGroupTeacher(
            String eduYear,
            int subjectNo,
            int groupCode,
            int tutorNo,
            int page,
            int numberPerPage);

    /**
     * - отримати всіх студентів за роком навчання/ предметом / групою / викладачем
     */
    List<Student> findAllStudentsByYearSubjectGroupTeacher(
            String eduYear,
            String subject,
            String groupName,
            String tutorName,
            int page,
            int numberPerPage);

    /**
     * - отримати всіх студентів за роком навчання/ предметом / групою / викладачем
     */
    List<Student> findAllStudentsByYearSubjectGroupTeacher(
            String eduYear,
            int subjectNo,
            int groupCode,
            int tutorNo,
            int page,
            int numberPerPage);


    /**
     * -  отримати n% перших студентів за рейтингом (на спеціальності за семестр)    НАЙКРАЩИХ
     */
    List<Student> findBestPercentOfStudentByGroupCourse(
            int percent,
            int groupCode,
            int course,
            int page,
            int numberPerPage);

    /**
     * -  отримати n% перших студентів за рейтингом (на спеціальності за семестр)   НАЙКРАЩИХ
     */
    List<Student> findBestPercentOfStudentByGroupCourse(
            int percent,
            String groupName,
            int course,
            int page,
            int numberPerPage);

    /**
     * -  отримати n% перших студентів за рейтингом (на спеціальності за семестр)     ГІРШИХ
     */
    List<Student> findWorstPercentOfStudentByGroupCourse(
            int percent,
            int groupCode,
            int course,
            int page,
            int numberPerPage);

    /**
     * -  отримати n% перших студентів за рейтингом (на спеціальності за семестр)     ГІРШИХ
     */

    List<Student> findWorstPercentOfStudentByGroupCourse(
            int percent,
            String groupName,
            int course,
            int page,
            int numberPerPage);

    /**
     * - генерувати список студентів за рейтингом для group\курсу
     */

    List<Student> sortAllStudentInGroupCourseByFromHighToLowRating(
            int percent,
            String groupName,
            int course,
            int page,
            int numberPerPage);


    /**
     * - генерувати список студентів за рейтингом для group\курсу
     */

    List<Student> sortAllStudentInGroupCourseByFromHighToLowRating(
            int percent,
            int groupCode,
            int course,
            int page,
            int numberPerPage);

    /**
     * - генерувати список студентів за рейтингом для group\курсу
     */

    List<Student> sortAllStudentInGroupCourseByFromLowToHighRating(
            int percent,
            String groupName,
            int course,
            int page,
            int numberPerPage);


    /**
     * - генерувати список студентів за рейтингом для group\курсу
     */

    List<Student> sortAllStudentInGroupCourseByFromLowToHighRating(
            int percent,
            int groupCode,
            int course,
            int page,
            int numberPerPage);

/**
 - отримати остаточну оцінку/ бігунці / середній бал/ всі оцінки студента за ПІБ
 * */

    /**
     * - отримати бігунці студента за ПІБ
     */
    public List<Vidomist> findAllVidomostyByStudentId(int studentId,
                                                      int page,
                                                      int numberPerPage);

    /**
     * - отримати бігунці студента за ПІБ
     */
    public List<Vidomist> findAllVidomostyByStudentPIB(String name,
                                                       String surname,
                                                       String patronymic,
                                                       int page,
                                                       int numberPerPage);

    /**
     * - отримати середній бал студента за ПІБ
     */
    public double findAverageMarkForStudentById(int studentCode);

    /**
     * - отримати середній бал студента за ПІБ
     */
    public double findAverageMarkForStudentByPIB(String name,
                                                 String surname,
                                                 String patronymic);

    /**
     * - отримати всі оцінки студента за ПІБ
     */
    public TreeMap<Integer, List> findAllMarksForStudentByPIB(String name,
                                                                 String surname,
                                                                 String patronymic,
                                                                 int page,
                                                                 int numberPerPage);

    /**
     * - отримати всі оцінки студента за ПІБ
     */
    public TreeMap<Integer, List> findAllMarksForStudentById(int studentCode,
                                                                int page,
                                                                int numberPerPage);

    /** - отримати остаточну оцінку/ бігунці / середній бал/ всі оцінки студента за ПІБ*/

}
