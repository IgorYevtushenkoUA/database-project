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

    public Student findById(int studentId) {
        return studentDao.findById(studentId);
    }

    public List<String> findNames() {
        return studentDao.findNames();
    }

    public List<String> findNames(String name) {
        return studentDao.findNames(name);
    }

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

    public TreeMap<String, Double> findAverageStudentMarksTrimCourse(int studentId,
                                                                     String trim,
                                                                     String course,
                                                                     String eduYear,
                                                                     String sortType,
                                                                     String sortGrow,
                                                                     int page,
                                                                     int numberPerPage) {
        return studentDao.findAverageStudentMarksTrimCourse(studentId, trim, course, eduYear, sortType, sortGrow, page, numberPerPage);

    }


    public List<Student> findAll(int page, int numberPerPage) {
        return studentDao.findAll(page, numberPerPage);
    }

    public List<Student> findByPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        return studentDao.findByPIB(name, surname, patronymic, page, numberPerPage);
    }

    public List<Student> findByNameSurname(String name, String surname, int page, int numberPerPage) {
        return studentDao.findByNameSurname(name, surname, page, numberPerPage);
    }

    public List<Vidomist> findAllVidomostyByStudentId(int studentId, int page, int numberPerPage) {
        return studentDao.findAllVidomostyByStudentId(studentId, page, numberPerPage);
    }

    public List<Vidomist> findAllVidomostyByStudentPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        return studentDao.findAllVidomostyByStudentPIB(name, surname, patronymic, page, numberPerPage);
    }

    public List<Bigunets> findAllBigunetsByStudentId(int studentId, int page, int numberPerPage) {
        return studentDao.findAllBigunetsByStudentId(studentId, page, numberPerPage);

    }

    public double findAverageMarkById(int studentCode) {
        return findAverageMarkById(studentCode);
    }

    public double findAverageMarkByPIB(String name, String surname, String patronymic) {
        return studentDao.findAverageMarkByPIB(name, surname, patronymic);
    }

    public TreeMap<Integer, List> findAllMarksByPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        return studentDao.findAllMarksByPIB(name, surname, patronymic, page, numberPerPage);
    }

    public TreeMap<Integer, List> findAllMarksById(int studentCode, int page, int numberPerPage) {
        return studentDao.findAllMarksById(studentCode, page, numberPerPage);
    }

    public void deleteById(int studentId) {
        studentDao.deleteById(studentId);
    }


    public List<Student> findAllByYearSubjectGroupTeacherTrimCourse(String eduYear,
                                                                    String subjectName,
                                                                    String groupName,
                                                                    String tutorName,
                                                                    String trim,
                                                                    String course,
                                                                    String sortType,
                                                                    String sortGrow,
                                                                    int page,
                                                                    int numberPerPage) {
        return studentDao.findAllByYearSubjectGroupTeacherTrimCourse(
                eduYear,
                subjectName,
                groupName,
                tutorName,
                trim,
                course,
                sortType,
                sortGrow,
                page,
                numberPerPage);
    }


    public List<Student> findAllDebtorsByYearSubjectGroupTeacherTrimCourse(String eduYear,
                                                                           String subjectName,
                                                                           String groupName,
                                                                           String tutorName,
                                                                           String trim,
                                                                           String course,
                                                                           String sortType,
                                                                           String sortGrow,
                                                                           int page,
                                                                           int numberPerPage) {
        return studentDao.findAllDebtorsByYearSubjectGroupTeacherTrimCourse(
                eduYear,
                subjectName,
                groupName,
                tutorName,
                trim,
                course,
                sortType,
                sortGrow,
                page,
                numberPerPage);
    }

}
