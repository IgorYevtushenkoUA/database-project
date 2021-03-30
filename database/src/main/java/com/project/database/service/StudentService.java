package com.project.database.service;

import com.project.database.dao.inter.StudentDao;
import com.project.database.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    public List<Student> findAllStudents(int page, int numberPerPage) {
        return studentDao.findAllStudents(page, numberPerPage);
    }

}
