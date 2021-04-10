package com.project.database.repository;

import com.project.database.entities.VidomistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VidomistRepository extends JpaRepository<VidomistEntity, Integer> {

    @Query("select v.vidomistNo,v.tutor.tutorSurname,v.tutor.tutorName,v.tutor.tutorPatronymic,v.group.groupCode, v.controlType, v.presentCount,v.absentCount,v.rejectedCount,v.examDate, sub.subjectName " +
            "from StudentEntity s " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.studentCode=s.studentCode " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo = g.subject.subjectNo " +
            "where " +
            "   s.studentCode=:studentCode ")
    List<List<String>> findAllStudentVidomosties(@Param("studentCode") int studentCode);

    VidomistEntity findByVidomistNo(Integer vidomistNo);

    @Query("select v " +
            "from VidomistEntity v " +
            "inner join TutorEntity t on t.tutorNo=v.tutor.tutorNo " +
            "where t.tutorNo=:tutorNo ")
    List<VidomistEntity> findAllByTutorNo(@Param("tutorNo") Integer tutorNo);

    @Query("select v " +
            "from VidomistEntity v " +
            "inner join GroupEntity g on v.group.groupCode = g.groupCode " +
            "inner join SubjectEntity s on s.subjectNo=g.subject.subjectNo " +
            "where s.subjectNo=:subjectNo")
    List<VidomistEntity> findAllBySubjectNo(@Param("subjectNo") Integer subjectNo);

    @Query("select v " +
            "from VidomistEntity v " +
            "inner join GroupEntity g on v.group.groupCode=g.groupCode " +
            "where g.groupName=:groupName "
    )
    List<VidomistEntity> findAllByGroupName(@Param("groupName") String groupName);

    // недопуски
    @Query("select sum(v.rejectedCount)" +
            "from VidomistEntity v " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "where g.groupCode=:groupCode")
    int nonAdmissionByGroupCode(@Param("groupCode") Integer groupCode);

    @Query("select sum(v.rejectedCount)" +
            "from VidomistEntity v " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo=g.subject.subjectNo " +
            "where sub.subjectNo=:subjectNo")
    int nonAdmissionBySubjectCode(@Param("subjectNo") Integer subjectNo);

    @Query("select sum(v.rejectedCount)" +
            "from VidomistEntity v " +
            "inner join TutorEntity t on t.tutorNo=v.tutor.tutorNo " +
            "where t.tutorNo=:tutorNo")
    int nonAdmissionByTeacherNo(@Param("tutorNo") Integer tutorNo);

}
