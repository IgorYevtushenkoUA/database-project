package com.project.database.dao.inter;

import com.project.database.entity.Bigunets;
import com.project.database.entity.Student;
import com.project.database.entity.Vidomist;

import java.util.List;
import java.util.TreeMap;

public interface StudentDao {

    Student findById(int studentId);

    Student findByStudentRecordBook(String studentRecordBook);

    List<String> findNames();

    List<String> findNames(String name);


    List<List<Object>> findAverageStudentsMarksTrimCourse(String trim,
                                                          String course,
                                                          String eduYear,
                                                          String sortType,
                                                          String sortGrow,
                                                          int page,
                                                          int numberPerPage);

    List<Object> findAverageStudentMarksTrimCourse(int studentId,
                                                   String trim,
                                                   String course,
                                                   String eduYear,
                                                   String sortType,
                                                   String sortGrow,
                                                   int page,
                                                   int numberPerPage);

    List<Object> findStudentMarksByTrimCourse(int studentId,
                                              String trim,
                                              String course,
                                              int page,
                                              int numberPerPage);

    List<Object> findAllWhoHasRetakeSubjectTrimEduYear(String subjectName,
                                                       String trim,
                                                       String eduYear,
                                                       String sortType,
                                                       String sortGrow,
                                                       int page,
                                                       int numberPerPage);

    List<Object> findAllRetakenSubjectForStudentTrimEduYear(int studentId,
                                                            String trim,
                                                            String eduYear,
                                                            String sortType,
                                                            String sortGrow,
                                                            int page,
                                                            int numberPerPage);

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
     * @param {String} eduYear      -> [2020-2021]
     * @param {String} subjectName
     * @param {String} groupName
     * @param {String} tutorName    -> []
     * @param {int}    trim
     * @param {int}    course
     * @param {String} sortType     -> [default: student_surname; else: or complete_mark or student_surname]
     * @param {String} sortGrow     -> [default: ASC, else DESC ]
     * @param {int}    page
     * @param {int}    numberPerPage
     * @return List<Student></>
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
     *
     * @param {String} eduYear      -> [2020-2021]
     * @param {String} subjectName
     * @param {String} groupName
     * @param {String} tutorName    -> []
     * @param {int}    trim
     * @param {int}    course
     * @param {String} sortType     -> [default: student_surname; else: or complete_mark or student_surname]
     * @param {String} sortGrow     -> [default: ASC, else DESC ]
     * @param {int}    page
     * @param {int}    numberPerPage
     * @return List<Student></>
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
