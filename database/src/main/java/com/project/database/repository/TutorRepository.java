package com.project.database.repository;

import com.project.database.entities.TutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TutorRepository extends JpaRepository<TutorEntity, Integer> {

    void deleteByTutorNo(int tutorNo);

    List<TutorEntity> findAll();

    @Query("select t.tutorSurname, t.tutorName, t.tutorPatronymic from TutorEntity t order by t.tutorSurname")
    List<List<String>> findAllTutorNames();

    @Query("select t.tutorSurname, t.tutorName, t.tutorPatronymic from TutorEntity t where t.tutorSurname like: name or t.tutorName like:name or t.tutorPatronymic like:name order by t.tutorSurname")
    List<List<String>> findAllTutorNamesByPartOFName(@Param("name") String name);

    // List<GroupEntity> findDistinctAllByGroupNameIn(@Param("groupName") List<String> groupName);
    List<TutorEntity> findDistinctByTutorNoIn(@Param("tutorNo") List<Integer> tutorNo);

    TutorEntity findByTutorSurnameAndTutorNameAndTutorPatronymic(String surname, String name, String patronymic);

}
