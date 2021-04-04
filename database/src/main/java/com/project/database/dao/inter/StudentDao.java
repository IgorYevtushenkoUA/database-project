package com.project.database.dao.inter;

import com.project.database.entity.Bigunets;
import com.project.database.entity.Student;
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
    List<Student> findAll(int page,
                          int numberPerPage);

    /**
     * Знайти студентів за (ім'я + прізвище + по-батькові)
     */
    List<Student> findByPIB(String name,
                            String surname,
                            String patronymic,
                            int page,
                            int numberPerPage);

    /**
     * Знайти студентів за (ім'я + прізвище + по-батькові)
     */
    List<Student> findByNameSurname(String name,
                                    String surname,
                                    int page,
                                    int numberPerPage);

    /**
     * - отримати відомості студента за ПІБ
     */
    List<Vidomist> findAllVidomostyByStudentId(int studentId,
                                               int page,
                                               int numberPerPage);

    /**
     * - отримати бігунці студента за ПІБ
     */
    List<Bigunets> findAllBigunetsByStudentId(int studentId,
                                              int page,
                                              int numberPerPage);

    /**
     * - отримати бігунці студента за ПІБ
     */
    List<Vidomist> findAllVidomostyByStudentPIB(String name,
                                                String surname,
                                                String patronymic,
                                                int page,
                                                int numberPerPage);

    /**
     * - отримати середній бал студента за ПІБ
     */
    double findAverageMarkById(int studentCode);

    /**
     * - отримати середній бал студента за ПІБ
     */
    double findAverageMarkByPIB(String name,
                                String surname,
                                String patronymic);

    /**
     * - отримати всі оцінки студента за ПІБ
     */
    TreeMap<Integer, List> findAllMarksByPIB(String name,
                                             String surname,
                                             String patronymic,
                                             int page,
                                             int numberPerPage);

    /**
     * - отримати всі оцінки студента за ПІБ
     */
    TreeMap<Integer, List> findAllMarksById(int studentCode,
                                            int page,
                                            int numberPerPage);

    /** - отримати остаточну оцінку/ бігунці / середній бал/ всі оцінки студента за ПІБ*/


    /**
     * - delete student by ID
     */
    void deleteById(int studentId);


    /**
     * - отримати всіх студентів за роком навчання / предметом / групою / викладачем
     */
    List<Student> findAllByYearSubjectGroupTeacherTrimCourse(
            String eduYear,
            String subjectName,
            String groupName,
            String tutorName,
            String trim,
            String course,
            String sortType,
            String sortGrow,
            int page,
            int numberPerPage);

    /**
     * - отримати всіх боржників за роком навчання / предметом / групою / викладачем
     */
    List<Student> findAllDebtorsByYearSubjectGroupTeacherTrimCourse(
            String eduYear,
            String subjectName,
            String groupName,
            String tutorName,
            String trim,
            String course,
            String sortType,
            String sortGrow,
            int page,
            int numberPerPage);

}
