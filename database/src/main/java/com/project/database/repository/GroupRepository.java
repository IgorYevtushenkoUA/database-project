package com.project.database.repository;

import com.project.database.entities.GroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {

    List<GroupEntity> findAll();

    void deleteByGroupCode(int groupId);

    @Query("select distinct(g.groupName) from GroupEntity g order by g.groupName")
    Page<GroupEntity> findAllByGroupName(Pageable pageable);

    @Query("select distinct(g.groupName) from GroupEntity g order by g.groupName")
    List<String> findAllGroupNames();

    @Query("select distinct(g.eduYear) from GroupEntity g")
    Page<String> findAllByEduYear(Pageable pageable);

    @Query("select distinct(g.eduYear) from GroupEntity g")
    List<String> findAllGroupEduYears();
}
