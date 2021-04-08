package com.project.database.repository;

import com.project.database.entities.GroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {

    void deleteByGroupCode(int groupId);

    @Query("select distinct(g.groupName) from GroupEntity g order by g.groupName")
    Page<GroupEntity> findAllByGroupName(Pageable pageable);

    @Query("select distinct(g.groupName) from GroupEntity g order by g.groupName")
    List<String> findAllGroupNames();

    @Query("select distinct(g.eduYear) from GroupEntity g")
    Page<String> findAllByEduYear(Pageable pageable);

    @Query("select distinct(g.eduYear) from GroupEntity g")
    List<String> findAllGroupEduYears();

    //
    /******************************* ALL YOU NEED *************************************/
    //    @Query("SELECT distinct g.trim FROM GroupEntity g WHERE g.trim IN :semesters")
    List<GroupEntity> findDistinctAllByTrimIn(@Param("semesters") List<String> semesters);

    //    @Query("SELECT distinct(g.course) FROM GroupEntity g WHERE g.course IN :courses")
    List<GroupEntity> findDistinctAllByCourseIn(@Param("courses") List<Integer> courses);

    // " select distinct(g.eduYear) from GroupEntity g where g.eduYear IN '" + eduYear + "' "
    List<GroupEntity> findDistinctAllByEduYearIn(@Param("eduYears") List<String> eduYears);
    /**********************************************************************************/
}
