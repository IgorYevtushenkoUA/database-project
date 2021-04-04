package com.project.database.service;

import com.project.database.dao.inter.TutorDao;
import com.project.database.entity.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    @Autowired
    private TutorDao tutorDao;

    public List<Tutor> findAll(int page, int numberPerPage){
        return tutorDao.findAll(page, numberPerPage);
    }

}
