package com.project.database.repository;

import com.project.database.entities.StatementFooterEntity;
import com.project.database.entities.StatementHeaderEntity;
import com.project.database.entities.StatementStudentEntity;
import com.project.database.entities.VidomistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VidomistRepository extends JpaRepository<VidomistEntity, Integer> {

    @Query("select v.vidomistNo,t.tutorSurname,t.tutorName,t.tutorPatronymic, sub.subjectName,g.groupName, v.controlType, v" +
            ".presentCount,v.absentCount,v.rejectedCount, v.examDate " +
            "from StudentEntity s " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.studentCode=s.studentCode " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo = g.subject.subjectNo " +
            "inner join TutorEntity t on t.tutorNo=v.tutor.tutorNo " +
            "where " +
            "   s.studentCode=:studentCode ")
    Page<Object[]> findAllStudentVidomosties(@Param("studentCode") int studentCode, Pageable pageable);

    VidomistEntity findByVidomistNo(Integer vidomistNo);

    @Query("select v.vidomistNo,t.tutorSurname,t.tutorName,t.tutorPatronymic, sub.subjectName,g.groupName, " +
            "v.controlType, " +
            "v.presentCount,v.absentCount,v.rejectedCount, v.examDate " +
            "from StudentEntity s " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.studentCode=s.studentCode " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo = g.subject.subjectNo " +
            "inner join TutorEntity t on t.tutorNo=v.tutor.tutorNo " +
            "where " +
            "   sub.subjectName in (:subjectName) " +
            "and " +
            "   t.tutorNo in (:tutorNo) " +
            "and " +
            "   g.groupName in (:groupName) " +
            "group by v.vidomistNo,t.tutorSurname,t.tutorName,t.tutorPatronymic, sub.subjectName,g.groupName,v.controlType, v.presentCount,v.absentCount,v.rejectedCount, v.examDate "
    )
    Page<Object[]> findAllStatements(
            @Param("subjectName") List<String> subjectName,
            @Param("tutorNo") List<Integer> tutorNo,
            @Param("groupName") List<String> groupName,
            Pageable pageable);

    @Query("select v " +
            "from VidomistEntity v " +
            "inner join TutorEntity t on v.tutor.tutorNo = t.tutorNo " +
            "where t.tutorNo=:tutorNo")
    Page<VidomistEntity> findAllByTutorNo(@Param("tutorNo") Integer tutorNo, Pageable pageable);

    @Query("select v " +
            "from VidomistEntity v " +
            "inner join GroupEntity g on v.group.groupCode = g.groupCode " +
            "inner join SubjectEntity s on s.subjectNo=g.subject.subjectNo " +
            "where s.subjectNo=:subjectNo")
    Page<VidomistEntity> findAllBySubjectNo(@Param("subjectNo") Integer subjectNo, Pageable pageable);

    @Query("select v " +
            "from VidomistEntity v " +
            "inner join GroupEntity g on v.group.groupCode=g.groupCode " +
            "where g.groupName=:groupName "
    )
    Page<VidomistEntity> findAllByGroupName(@Param("groupName") String groupName, Pageable pageable);

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

    @Query("select v " +
            "from VidomistEntity v " +
            "inner join GroupEntity gr on gr.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo=gr.subject.subjectNo " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.vidomistNo=v.vidomistNo " +
            "inner join StudentEntity s on s.studentCode=vm.vidomistMarkId.studentCode " +
            "where " +
            "   s.studentRecordBook=:studentRecordBook " +
            "and " +
            "   sub.subjectName=:subjectName ")
    VidomistEntity findVidomistNoByStudentRecordBookAndSubjectName(
            @Param("studentRecordBook") String studentRecordBook,
            @Param("subjectName") String subjectName
    );

    @Query("select new com.project.database.entities.StatementHeaderEntity(v.vidomistNo, sub.eduLevel, sub.faculty, g.course, g.groupName, sub.subjectName, g.trim, v.controlType, v.examDate, t.tutorSurname,t.tutorName,t.tutorPatronymic,t.position, t.academStatus) " +
            "from StudentEntity s " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.studentCode=s.studentCode " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo = g.subject.subjectNo " +
            "inner join TutorEntity t on t.tutorNo=v.tutor.tutorNo " +
            "where " +
            "   v.vidomistNo=:vidomistNo ")
    List<StatementHeaderEntity> getStatementHeader(@Param("vidomistNo") Integer vidomistNo);


    @Query("select new com.project.database.entities.StatementFooterEntity(v.presentCount, v.absentCount, v.rejectedCount) " +
            "from VidomistEntity v " +
            "where " +
            "   v.vidomistNo=:vidomistNo ")
    List<StatementFooterEntity> getStatementFooter(@Param("vidomistNo") Integer statementId);

    @Query("select new com.project.database.entities.StatementStudentEntity(s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic, s.studentRecordBook, vm.trimMark,  vm.markCheck, vm.completeMark, vm.natMark, vm.ectsMark) " +
            "from StudentEntity s " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.studentCode=s.studentCode " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo = g.subject.subjectNo " +
            "inner join TutorEntity t on t.tutorNo=v.tutor.tutorNo " +
            "where " +
            "   v.vidomistNo=:vidomistNo ")
    List<StatementStudentEntity> getStatementStudent(@Param("vidomistNo") Integer statementId);

}
