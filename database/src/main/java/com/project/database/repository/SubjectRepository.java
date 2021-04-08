package com.project.database.repository;

import com.project.database.entities.SubjectEntity;
import com.project.database.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

    void deleteBySubjectNo(int subjectId);

    Page<SubjectEntity> findAll(Pageable pageable);

    List<SubjectEntity> findAll();

    @Query("select s.subjectName from SubjectEntity s order by s.subjectName")
    List<String> findAllSubjectNames();

    @Query("select s.subjectName from SubjectEntity s where s.subjectName like:name order by s.subjectName")
    List<String> findAllSubjectNames(@Param("name") String name);

    List<SubjectEntity> findDistinctBySubjectNameIn(@Param("subjectName") List<String> subjectName);

}
