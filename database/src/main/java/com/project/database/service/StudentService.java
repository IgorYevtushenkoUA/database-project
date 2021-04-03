package com.project.database.service;

import com.project.database.dao.inter.StudentDao;
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

    public List<Student> findAll(int page, int numberPerPage) {
        return studentDao.findAll(page, numberPerPage);
    }

    public List<Student> findByPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        return studentDao.findByPIB(name, surname, patronymic, page, numberPerPage);
    }

    public List<Student> findByNameSurname(String name, String surname, int page, int numberPerPage) {
        return studentDao.findByNameSurname(name, surname, page, numberPerPage);
    }

    public List<Student> findAllDebtorsByYearSubjectGroupTeacher(String eduYear, String subjectName, String groupName, String tutorName, int page, int numberPerPage) {
        return studentDao.findAllDebtorsByYearSubjectGroupTeacher(eduYear, subjectName, groupName, tutorName, page, numberPerPage);
    }

    public List<Student> findAllDebtorsByYearSubjectGroupTeacher(String eduYear, int subjectNo, int groupCode, int tutorNo, int page, int numberPerPage) {
        return studentDao.findAllDebtorsByYearSubjectGroupTeacher(eduYear, subjectNo, groupCode, tutorNo, page, numberPerPage);
    }

    public List<Student> findAllByYearSubjectGroupTeacher(String eduYear, String subjectName, String groupName, String tutorName, int page, int numberPerPage) {
        return studentDao.findAllByYearSubjectGroupTeacher(eduYear, subjectName, groupName, tutorName, page, numberPerPage);
    }

    public List<Student> findAllByYearSubjectGroupTeacher(String eduYear, int subjectNo, int groupCode, int tutorNo, int page, int numberPerPage) {
        return studentDao.findAllByYearSubjectGroupTeacher(eduYear, subjectNo, groupCode, tutorNo, page, numberPerPage);
    }

    public List<Vidomist> findAllVidomostyByStudentId(int studentId, int page, int numberPerPage) {
        return studentDao.findAllVidomostyByStudentId(studentId, page, numberPerPage);
    }

    public List<Vidomist> findAllVidomostyByStudentPIB(String name, String surname, String patronymic, int page, int numberPerPage) {
        return studentDao.findAllVidomostyByStudentPIB(name, surname, patronymic, page, numberPerPage);
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
    public void deleteById(int studentId){
        studentDao.deleteById(studentId);
    }


}
