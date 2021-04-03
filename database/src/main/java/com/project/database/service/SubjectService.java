package com.project.database.service;

import com.project.database.dao.inter.SubjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    @Autowired
    private SubjectDao subjectDao;


    public void deleteByID(int subjectId) {
    subjectDao.deleteByID(subjectId);
    }
}
