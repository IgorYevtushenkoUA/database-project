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

    public List<Vidomist> findAllVidomistsByTutorGroupSubject(String tutorName, String groupName, String subjectName, int page, int numberPerPage) {
        return vidomistDao.findAllVidomistsByTutorGroupSubject(tutorName, groupName, subjectName, page, numberPerPage);
    }

}
