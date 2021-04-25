package com.project.database.repository;

import com.project.database.dto.bigunets.shortInfo.BigunetsShortInfo;
import com.project.database.entities.BigunetsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BigunetsRepository extends JpaRepository<BigunetsEntity, Integer> {

    @Query("select b.bigunetsNo, concat(b.tutor.tutorSurname,' ', b.tutor.tutorName,' ', b.tutor.tutorPatronymic), sub.subjectName, b.controlType, b.postpReason, b.examDate, b.validUntil " +
            "from BigunetsEntity b " +
            "inner join BigunetsMarkEntity bm on bm.bigunetsMarkId.bigunetsNo=b.bigunetsNo " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.vidomistNo=bm.bigunetsMarkId.vidomistNo " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo=g.subject.subjectNo " +
            "inner join StudentEntity s on s.studentCode=bm.bigunetsMarkId.studentCode " +
            "inner join TutorEntity t on t.tutorNo= v.tutor.tutorNo " +
            "where b.bigunetsNo=:bigunetsNo " +
            "group by b.bigunetsNo, concat(b.tutor.tutorSurname,' ', b.tutor.tutorName,' ', b.tutor.tutorPatronymic), sub.subjectName, b.controlType, b.postpReason, b.examDate, b.validUntil")
    Page<Object[]> findBigunetsById(@Param("bigunetsNo") Integer bigunetsNo, Pageable pageable);

    @Query("select b.bigunetsNo, b.tutor.tutorSurname, b.tutor.tutorName, b.tutor.tutorPatronymic, sub.subjectName, b.controlType, b.postpReason, b.examDate, b.validUntil " +
            "from BigunetsEntity b " +
            "inner join BigunetsMarkEntity bm on bm.bigunetsMarkId.bigunetsNo=b.bigunetsNo " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.vidomistNo=bm.bigunetsMarkId.vidomistNo " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo=g.subject.subjectNo " +
            "inner join StudentEntity s on s.studentCode=bm.bigunetsMarkId.studentCode " +
            "inner join TutorEntity t on t.tutorNo= v.tutor.tutorNo " +
            "where " +
            "   sub.subjectName in (:subjectName) " +
            "and " +
            "   t.tutorNo in (:tutorNo) " +
            "and " +
            "   g.groupName in (:groupName) " +
            "group by b.bigunetsNo, b.tutor.tutorSurname, b.tutor.tutorName, b.tutor.tutorPatronymic, sub.subjectName, b.controlType, b.postpReason, b.examDate, b.validUntil ")
    Page<Object[]> findAllBiguntsy(
            @Param("tutorNo") List<Integer> tutorNo,
            @Param("subjectName") List<String> subjectName,
            @Param("groupName") List<String> groupName,
            Pageable pageable
    );


    @Query("select b.bigunetsNo, b.tutor.tutorSurname, b.tutor.tutorName, b.tutor.tutorPatronymic, sub.subjectName, b.controlType, b.postpReason, b.examDate, b.validUntil " +
            "from BigunetsEntity b " +
            "inner join BigunetsMarkEntity bm on bm.bigunetsMarkId.bigunetsNo=b.bigunetsNo " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.vidomistNo=bm.bigunetsMarkId.vidomistNo " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo=g.subject.subjectNo " +
            "inner join StudentEntity s on s.studentCode=bm.bigunetsMarkId.studentCode " +
            "inner join TutorEntity t on t.tutorNo= v.tutor.tutorNo " +
            "where s.studentCode =:studentCode ")
    Page<Object[]> findAllStudentBigunets(
            @Param("studentCode") Integer studentCode,
            Pageable pageable
    );

    BigunetsEntity findByBigunetsNo(Integer bigunetsNo);

    @Query("select b.bigunetsNo, b.tutor.tutorSurname, b.tutor.tutorName, b.tutor.tutorPatronymic, sub.subjectName, b.controlType, b.postpReason, b.examDate, b.validUntil " +
            "from BigunetsEntity b " +
            "inner join BigunetsMarkEntity bm on bm.bigunetsMarkId.bigunetsNo=b.bigunetsNo " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.vidomistNo=bm.bigunetsMarkId.vidomistNo " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo=g.subject.subjectNo " +
            "inner join StudentEntity s on s.studentCode=bm.bigunetsMarkId.studentCode " +
            "inner join TutorEntity t on t.tutorNo= v.tutor.tutorNo " +
            "where t.tutorNo=:tutorNo ")
    Page<Object[]> findAllByTutorNo(@Param("tutorNo") Integer tutorNo, Pageable pageable);

    @Query("select b.bigunetsNo, b.tutor.tutorSurname, b.tutor.tutorName, b.tutor.tutorPatronymic, sub.subjectName, b.controlType, b.postpReason, b.examDate, b.validUntil " +
            "from BigunetsEntity b " +
            "inner join BigunetsMarkEntity bm on bm.bigunetsMarkId.bigunetsNo=b.bigunetsNo " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.vidomistNo=bm.bigunetsMarkId.vidomistNo " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo=g.subject.subjectNo " +
            "inner join StudentEntity s on s.studentCode=bm.bigunetsMarkId.studentCode " +
            "inner join TutorEntity t on t.tutorNo= v.tutor.tutorNo " +
            "where g.groupName=:groupName")
    Page<Object[]> findAllByGroupName(@Param("groupName") String groupName, Pageable pageable);

    @Query("select b.bigunetsNo, b.tutor.tutorSurname, b.tutor.tutorName, b.tutor.tutorPatronymic, sub.subjectName, b.controlType, b.postpReason, b.examDate, b.validUntil " +
            "from BigunetsEntity b " +
            "inner join BigunetsMarkEntity bm on bm.bigunetsMarkId.bigunetsNo=b.bigunetsNo " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.vidomistNo=bm.bigunetsMarkId.vidomistNo " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo=g.subject.subjectNo " +
            "inner join StudentEntity s on s.studentCode=bm.bigunetsMarkId.studentCode " +
            "inner join TutorEntity t on t.tutorNo= v.tutor.tutorNo " +
            "where sub.subjectNo=:subjectNo")
    Page<Object[]> findAllBySubjectNo(@Param("subjectNo") Integer subjectNo, Pageable pageable);


    @Query("select b.bigunetsNo, b.tutor.tutorSurname, b.tutor.tutorName, b.tutor.tutorPatronymic, sub.subjectName, b.controlType, b.postpReason, b.examDate, b.validUntil " +
            "from BigunetsEntity b " +
            "inner join BigunetsMarkEntity bm on bm.bigunetsMarkId.bigunetsNo=b.bigunetsNo " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.vidomistNo=bm.bigunetsMarkId.vidomistNo " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo=g.subject.subjectNo " +
            "inner join StudentEntity s on s.studentCode=bm.bigunetsMarkId.studentCode " +
            "inner join TutorEntity t on t.tutorNo= v.tutor.tutorNo " +
            "where " +
            "   sub.subjectName in(:subjectName) " +
            "and " +
            "   t.tutorNo in (:tutorNo) " +
            "and " +
            "   g.groupName in (:groupName)" +
            "")
    Page<Object[]> findAllBiguntsiBySubjectNameTutorNoGroupName(
            @Param("subjectName") List<String> subjectName,
            @Param("tutorNo") List<Integer> tutorNo,
            @Param("groupName") List<String> groupName,
            Pageable pageable);

}
