package com.project.database.dao.inter;

import com.project.database.entity.Group;

import java.util.List;

public interface GroupDao {

    void deleteByID(int groupId);

    List<Group> findAllGroupName(int page, int numberPerPage);

    List<String> findAllEduYears(int page, int numberPerPage);

}
