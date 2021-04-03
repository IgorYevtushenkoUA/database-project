package com.project.database.service;

import com.project.database.dao.inter.StudentDao;
import com.project.database.entity.Student;
import com.project.database.entity.Subject;
import com.project.database.entity.Vidomist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;

@Service
//@Transactional
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    public List<Student> findAllStudents(int page, int numberPerPage) {
        return studentDao.findAllStudents(page, numberPerPage);
    }

    public List<Student> findStudentsByPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        return studentDao.findStudentsByPIB(name, surname, patronymic, page, numberPerPage);
    }

    public List<Student> findStudentsByNameSurname(String name, String surname, int page, int numberPerPage) {
        return studentDao.findStudentsByNameSurname(name, surname, page, numberPerPage);
    }

    public List<Student> findAllDebtorsByYearSubjectGroupTeacher(String eduYear, String subjectName, String groupName, String tutorName, int page, int numberPerPage) {
        return studentDao.findAllDebtorsByYearSubjectGroupTeacher(eduYear, subjectName, groupName, tutorName, page, numberPerPage);
    }

    public List<Student> findAllDebtorsByYearSubjectGroupTeacher(String eduYear, int subjectNo, int groupCode, int tutorNo, int page, int numberPerPage) {
        return studentDao.findAllDebtorsByYearSubjectGroupTeacher(eduYear, subjectNo, groupCode, tutorNo, page, numberPerPage);
    }

    public List<Student> findAllStudentsByYearSubjectGroupTeacher(String eduYear, String subjectName, String groupName, String tutorName, int page, int numberPerPage) {
        return studentDao.findAllStudentsByYearSubjectGroupTeacher(eduYear, subjectName, groupName, tutorName, page, numberPerPage);
    }

    public List<Student> findAllStudentsByYearSubjectGroupTeacher(String eduYear, int subjectNo, int groupCode, int tutorNo, int page, int numberPerPage) {
        return studentDao.findAllStudentsByYearSubjectGroupTeacher(eduYear, subjectNo, groupCode, tutorNo, page, numberPerPage);
    }

    public List<Vidomist> findAllVidomostyByStudentId(int studentId, int page, int numberPerPage) {
        return studentDao.findAllVidomostyByStudentId(studentId, page, numberPerPage);
    }

    public List<Vidomist> findAllVidomostyByStudentPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        return studentDao.findAllVidomostyByStudentPIB(name, surname, patronymic, page, numberPerPage);
    }

    public double findAverageMarkForStudentById(int studentCode) {
        return findAverageMarkForStudentById(studentCode);
    }

    public double findAverageMarkForStudentByPIB(String name, String surname, String patronymic) {
        return studentDao.findAverageMarkForStudentByPIB(name, surname, patronymic);
    }

    public TreeMap<Integer, List> findAllMarksForStudentByPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        return studentDao.findAllMarksForStudentByPIB(name, surname, patronymic, page, numberPerPage);
    }

    public TreeMap<Integer, List> findAllMarksForStudentById(int studentCode, int page, int numberPerPage) {
        return studentDao.findAllMarksForStudentById(studentCode, page, numberPerPage);
    }

}
