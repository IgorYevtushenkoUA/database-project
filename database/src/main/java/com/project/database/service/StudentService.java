package com.project.database.service;

import com.project.database.dao.inter.StudentDao;
import com.project.database.entity.Bigunets;
import com.project.database.entity.Student;
import com.project.database.entity.Vidomist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeMap;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    /**
     *  method finds Student by id
     * @param {int} studentId
     * @return Student
     */
    public Student findById(int studentId) {
        return studentDao.findById(studentId);
    }

    /**
     * method finds List od Student with their pib(surname, name, patronymic)
     * @return ["Кравченко Борис Іванович","",""]
     */
    public List<String> findNames() {
        return studentDao.findNames();
    }

    /**
     * method finds List od Student with their pib(surname, name, patronymic)
     * here method using sql LIKE to find similar STUDENT as text in input
     * @param {String} name
     * @return ["Кравченко Борис Іванович","",""]
     */
    public List<String> findNames(String name) {
        return studentDao.findNames(name);
    }

    /**
     * Find average marks for all student in (or trim, or course)
     * @param {String} trim         -> null or values(1,2,...)
     * @param {String} course       -> null or values(1,2,...)
     * @param {String} eduYear      -> null or values('2020-2021')
     * @param {String} sortType     -> values (student_surname|complete_mark)
     * @param {String} sortGrow     -> values (ASC|DESC)
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return [[5, Ландяк Андрій Петрович, 99.0], [6, Крейдун Андрій Миколайович, 65.0]...]
     */
    public List<List<Object>> findAverageStudentsMarksTrimCourse(String trim,
                                                                 String course,
                                                                 String eduYear,
                                                                 String sortType,
                                                                 String sortGrow,
                                                                 int page,
                                                                 int numberPerPage) {
        return studentDao.findAverageStudentsMarksTrimCourse(trim, course,
                eduYear, sortType, sortGrow, page, numberPerPage);
    }

    /**
     * Find average marks for student by ID in (or trim, or course)
     * @param {int} studentId       -> values (1,2,...)
     * @param {String} trim         -> null or values(1,2,...)
     * @param {String} course       -> null or values(1,2,...)
     * @param {String} eduYear      -> null or values('2020-2021')
     * @param {String} sortType     -> values (student_surname|complete_mark)
     * @param {String} sortGrow     -> values (ASC|DESC)
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return [2, Синицин Владислав Олександрович, 81.5]
     */
    public List<Object> findAverageStudentMarksTrimCourse(int studentId,
                                                          String trim,
                                                          String course,
                                                          String eduYear,
                                                          String sortType,
                                                          String sortGrow,
                                                          int page,
                                                          int numberPerPage) {
        return studentDao.findAverageStudentMarksTrimCourse(studentId, trim, course, eduYear, sortType, sortGrow, page, numberPerPage);

    }

    /**
     * Find all marks for student by ID in (or trim, or course)
     * @param {int} studentId       -> values (1,2,...)
     * @param {String} trim         -> null or values(1,2,...)
     * @param {String} course       -> null or values(1,2,...)
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return [[Іванюк Назар Олександрович, БД, 91]]
     */
    public List<Object> findStudentMarksByTrimCourse(int studentId,
                                                     String trim,
                                                     String course,
                                                     int page,
                                                     int numberPerPage) {
        return studentDao.findStudentMarksByTrimCourse(studentId, trim, course, page, numberPerPage);
    }

    /**
     * Method to find all retaken subject for student by ID for (or trim, or eduYear)
     * @param {int} studentId       -> values(1,2,...)
     * @param {String} trim         -> null or values(1,2,...)
     * @param {String} eduYear      -> null or values('2020-2021')
     * @param {String} sortType     -> values (student_surname|complete_mark)
     * @param {String} sortGrow     -> values (ASC|DESC)
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return [[Кучерявий Вадим Юрійович, БД, 73]]
     */
    public List<Object> findAllRetakenSubjectForStudentTrimEduYear(int studentId,
                                                                   String trim,
                                                                   String eduYear,
                                                                   String sortType,
                                                                   String sortGrow,
                                                                   int page,
                                                                   int numberPerPage) {
        return studentDao.findAllRetakenSubjectForStudentTrimEduYear(studentId, trim, eduYear, sortType, sortGrow, page, numberPerPage);
    }

    /**
     * Method finds all student who retook subject in (or trim, or eduYear)
     * @param subjectName
     * @param {String} trim         -> null or values(1,2,...)
     * @param {String} eduYear      -> null or values('2020-2021')
     * @param {String} sortType     -> values (student_surname|complete_mark)
     * @param {String} sortGrow     -> values (ASC|DESC)
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return [[Крейдун Андрій Миколайович, БД, 61], [Кучерявий Вадим Юрійович, БД, 73],...]
     */
    public List<Object> findAllWhoHasRetakeSubjectTrimEduYear(String subjectName,
                                                              String trim,
                                                              String eduYear,
                                                              String sortType,
                                                              String sortGrow,
                                                              int page,
                                                              int numberPerPage) {
        return studentDao.findAllWhoHasRetakeSubjectTrimEduYear(subjectName, trim, eduYear, sortType, sortGrow, page, numberPerPage);
    }

    /**
     * Method find all students
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return
     */
    public List<Student> findAll(int page, int numberPerPage) {
        return studentDao.findAll(page, numberPerPage);
    }

    /**
     * method find list of student by same pib
     * @param name
     * @param surname
     * @param patronymic
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return
     */
    public List<Student> findByPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        return studentDao.findByPIB(name, surname, patronymic, page, numberPerPage);
    }

    /**
     *
     * @param name
     * @param surname
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return
     */
    public List<Student> findByNameSurname(String name, String surname, int page, int numberPerPage) {
        return studentDao.findByNameSurname(name, surname, page, numberPerPage);
    }

    /**
     * Method find all student's vidomosty by ID
     * @param studentId
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return
     */
    public List<Vidomist> findAllVidomostyByStudentId(int studentId, int page, int numberPerPage) {
        return studentDao.findAllVidomostyByStudentId(studentId, page, numberPerPage);
    }

    /**
     * Method find all student's vidomosty by PIB
     * @param name
     * @param surname
     * @param patronymic
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return
     */
    public List<Vidomist> findAllVidomostyByStudentPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        return studentDao.findAllVidomostyByStudentPIB(name, surname, patronymic, page, numberPerPage);
    }

    /**
     *
     * @param studentId
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return
     */
    public List<Bigunets> findAllBigunetsByStudentId(int studentId, int page, int numberPerPage) {
        return studentDao.findAllBigunetsByStudentId(studentId, page, numberPerPage);

    }

    /**
     *
     * @param studentCode
     * @return
     */
    public double findAverageMarkById(int studentCode) {
        return studentDao.findAverageMarkById(studentCode);
    }

    /**
     *
     * @param name
     * @param surname
     * @param patronymic
     * @return
     */
    public double findAverageMarkByPIB(String name, String surname, String patronymic) {
        return studentDao.findAverageMarkByPIB(name, surname, patronymic);
    }

    /**
     *
     * @param name
     * @param surname
     * @param patronymic
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return
     */
    public TreeMap<Integer, List> findAllMarksByPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        return studentDao.findAllMarksByPIB(name, surname, patronymic, page, numberPerPage);
    }

    /**
     *
     * @param studentCode
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return
     */
    public TreeMap<Integer, List> findAllMarksById(int studentCode, int page, int numberPerPage) {
        return studentDao.findAllMarksById(studentCode, page, numberPerPage);
    }

    /**
     *
     * @param studentId
     */
    public void deleteById(int studentId) {
        studentDao.deleteById(studentId);
    }

    /**
     *
     * @param {String} eduYear      -> null or values('2020-2021')
     * @param {String} subjectName  -> null or values
     * @param {String} groupName    -> null or values
     * @param {String} tutorName    -> null or values
     * @param {String} trim         -> null or values(1,2,...)
     * @param {String} course       -> null or values(1,2,...)
     * @param {String} sortType     -> values (student_surname|complete_mark)
     * @param {String} sortGrow     -> values (ASC|DESC)
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return [Student(studentCode = 4, studentSurname = Бойко, studentName = Данило, studentPatronymic = Романович, studentRecordBook = 37452), Student(studentCode=3, studentSurname=Євтушенко, studentName=Ігор, studentPatronymic=Олегович, studentRecordBook=83921)]
     */
    public List<Student> findAllByYearSubjectGroupTeacherTrimCourse(String eduYear,
                                                                    String subjectName,
                                                                    String groupName,
                                                                    String tutorName,
                                                                    String trim,
                                                                    String course,
                                                                    String sortBy,
                                                                    Boolean sortDesc,
                                                                    int page,
                                                                    int numberPerPage) {
        String sortOrder = sortDesc ? "desc" : "asc";
        return studentDao.findAllByYearSubjectGroupTeacherTrimCourse(
                eduYear,
                subjectName,
                groupName,
                tutorName,
                trim,
                course,
                sortBy,
                sortOrder,
                page,
                numberPerPage);
    }

    /**
     *
     * @param {String} eduYear      -> null or values('2020-2021')
     * @param {String} subjectName  -> null or values
     * @param {String} groupName    -> null or values
     * @param {String} tutorName    -> null or values
     * @param {String} trim         -> null or values(1,2,...)
     * @param {String} course       -> null or values(1,2,...)
     * @param {String} sortType     -> values (student_surname|complete_mark)
     * @param {String} sortGrow     -> values (ASC|DESC)
     * @param {int} page            -> values (1,2,...)
     * @param {int} numberPerPage   -> values (1,2,...)
     * @return []
     */
    public List<Student> findAllDebtorsByYearSubjectGroupTeacherTrimCourse(String eduYear,
                                                                           String subjectName,
                                                                           String groupName,
                                                                           String tutorName,
                                                                           String trim,
                                                                           String course,
                                                                           String sortBy,
                                                                           Boolean sortDesc,
                                                                           int page,
                                                                           int numberPerPage) {
        String sortOrder = sortDesc ? "desc" : "asc";
        return studentDao.findAllDebtorsByYearSubjectGroupTeacherTrimCourse(
                eduYear,
                subjectName,
                groupName,
                tutorName,
                trim,
                course,
                sortBy,
                sortOrder,
                page,
                numberPerPage);
    }

}
