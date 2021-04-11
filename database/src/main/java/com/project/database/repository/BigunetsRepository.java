package com.project.database.repository;

import com.project.database.entities.BigunetsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BigunetsRepository extends JpaRepository<BigunetsEntity, Integer> {

    @Query("select b.bigunetsNo, b.examDate, b.validUntil, b.postpReason, b.controlType, b.tutor.tutorSurname, b.tutor.tutorName, b.tutor.tutorPatronymic, sub.subjectName " +
            "from BigunetsEntity b " +
            "inner join BigunetsMarkEntity bm on bm.bigunetsMarkId.bigunetsNo=b.bigunetsNo " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.vidomistNo=bm.bigunetsMarkId.vidomistNo " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo=g.subject.subjectNo " +
            "inner join StudentEntity s on s.studentCode=bm.bigunetsMarkId.studentCode " +
            "where s.studentCode =:studentCode")
    Page<List<String>> findAllStudentBigunets(
            @Param("studentCode") Integer studentCode,
            Pageable pageable
    );

    BigunetsEntity findByBigunetsNo(Integer bigunetsNo);

    @Query("select b " +
            "from BigunetsEntity b " +
            "inner join TutorEntity t on b.tutor.tutorNo=t.tutorNo " +
            "where t.tutorNo=:tutorNo ")
    Page<BigunetsEntity> findAllByTutorNo(@Param("tutorNo") Integer tutorNo, Pageable pageable);

    @Query("select b " +
            "from BigunetsEntity b " +
            "inner join BigunetsMarkEntity bm on bm.bigunetsMarkId.bigunetsNo=b.bigunetsNo " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.vidomistNo=bm.bigunetsMarkId.vidomistNo " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "where g.groupName=:groupName")
    Page<BigunetsEntity> findAllByGroupName(@Param("groupName") String groupName, Pageable pageable);

    @Query("select b " +
            "from BigunetsEntity b " +
            "inner join BigunetsMarkEntity bm on bm.bigunetsMarkId.bigunetsNo=b.bigunetsNo " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.vidomistNo=bm.bigunetsMarkId.vidomistNo " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity s on s.subjectNo=g.subject.subjectNo " +
            "where s.subjectNo=:subjectNo")
    Page<BigunetsEntity> findAllBySubjectNo(@Param("subjectNo") Integer subjectNo, Pageable pageable);


}
