package com.project.database.service;

import com.project.database.dao.inter.SubjectDao;
import com.project.database.entity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectDao subjectDao;

    public void deleteByID(int subjectId) {
        subjectDao.deleteByID(subjectId);
    }

    public List<Subject> findAll(int page, int numberPerPage) {
        return subjectDao.findAll(page, numberPerPage);
    }

    public List<String> findAllSubjectName() {
        return subjectDao.findAllSubjectName();
    }

    public List<String> findAllSubjectName(String name) {
        return subjectDao.findAllSubjectName(name);
    }

}

