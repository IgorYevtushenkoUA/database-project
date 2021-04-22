package com.project.database.service;

import com.project.database.dao.inter.GroupDao;
import com.project.database.dao.inter.SubjectDao;
import com.project.database.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupDao groupDao;

    public List<Group> findAllGroupName(int page, int numberPerPage) {
        return groupDao.findAllGroupName(page, numberPerPage);
    }

    public List<String> findAllEduYears(int page, int numberPerPage) {
        return groupDao.findAllEduYears(page, numberPerPage);
    }

}
