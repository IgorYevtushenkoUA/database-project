package com.project.database.service;

import com.project.database.dao.inter.VidomistDao;
import com.project.database.entity.Vidomist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VidomistService {

    @Autowired
    private VidomistDao vidomistDao;

    public List<Vidomist> findAllByTutorGroupSubject(String tutorName, String groupName, String subjectName, int page, int numberPerPage) {
        return vidomistDao.findAllByTutorGroupSubject(tutorName, groupName, subjectName, page, numberPerPage);
    }

    public void deleteById(int vidomistId) {
        vidomistDao.deleteById(vidomistId);
    }

    public List<Vidomist> findAll(int page, int numberPerPage) {
        return vidomistDao.findAll(page, numberPerPage);
    }

    public Vidomist findById(int id){
        return vidomistDao.findById(id);
    }
}
