package com.project.database.repository;

import com.project.database.additionalEntities.StudentMark;
import com.project.database.entities.StudentEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer>, StudentRepositoryCustom {

    StudentEntity findByStudentCode(int studentCode);

    StudentEntity findByStudentRecordBook(String recordBook);

    @Query("select s.studentSurname, s.studentName, s.studentPatronymic from StudentEntity s")
    List<List<String>> findAllStudentNames();

    @Query("select s.studentSurname, s.studentName, s.studentPatronymic from StudentEntity s where s.studentSurname like:name or s.studentName like:name or s.studentPatronymic like:name")
    List<List<String>> findAllStudentNames(@Param("name") String name);

    @Query("select s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic, avg(vm.completeMark) as completeMark " +
            "from StudentEntity s inner join VidomistMarkEntity vm on s.studentCode=vm.vidomistMarkId.studentCode " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity gr on gr.groupCode=v.group.groupCode " +
            "where " +
            "   gr.trim in (:semesters) " +
            "and " +
            "   gr.course in (:courses) " +
            "and " +
            "   gr.eduYear in (:eduYears) " +
            "group by s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic ")
    List<List<String>> findAverageStudentsMarksTrimCourse(
            @Param("semesters") List<String> semesters,
            @Param("courses") List<Integer> courses,
            @Param("eduYears") List<String> eduYears,
            Sort sort
    );

    @Query("select s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic, avg(vm.completeMark) as completeMark " +
            "from StudentEntity s inner join VidomistMarkEntity vm on s.studentCode=vm.vidomistMarkId.studentCode " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity gr on gr.groupCode=v.group.groupCode " +
            "where s.studentCode = :studentCode " +
            "and " +
            "   gr.trim in (:semesters) " +
            "and " +
            "   gr.course in (:courses) " +
            "and " +
            "   gr.eduYear in (:eduYears) " +
            "group by s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic ")
    List<List<String>> findAverageStudentMarksTrimCourse(
            @Param("studentCode") Integer studentCode,
            @Param("semesters") List<String> semesters,
            @Param("courses") List<Integer> courses,
            @Param("eduYears") List<String> eduYears,
            Sort sort);

    @Query("select s.studentSurname, s.studentName, s.studentPatronymic, s2.subjectName, bm.completeMark as completeMark " +
            "from StudentEntity s inner join VidomistMarkEntity vm on s.studentCode=vm.vidomistMarkId.studentCode " +
            "inner join BigunetsMarkEntity bm on bm.bigunetsMarkId.studentCode=vm.vidomistMarkId.studentCode " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity s2 on s2.subjectNo = g.groupCode " +
            "where " +
            "   s2.subjectName in (:subjectName) " +
            "and " +
            "   g.trim in (:trim) " +
            "and " +
            "   g.course in (:course) " +
            "and " +
            "   g.eduYear in (:eduYear) ")
    List<List<String>> findAllWhoHasRetakeSubjectTrimEduYear(@Param("subjectName") List<String> subjectName,
                                                             @Param("trim") List<String> trim,
                                                             @Param("course") List<Integer> course,
                                                             @Param("eduYear") List<String> eduYear,
                                                             Sort sort);

    @Query("select s.studentSurname, s.studentName, s.studentPatronymic, s2.subjectName, bm.completeMark as completeMark " +
            "from StudentEntity s inner join VidomistMarkEntity vm on s.studentCode=vm.vidomistMarkId.studentCode " +
            "inner join BigunetsMarkEntity bm on bm.bigunetsMarkId.studentCode=vm.vidomistMarkId.studentCode " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity s2 on s2.subjectNo = g.groupCode " +
            "where " +
            "   s.studentCode =:studentCode " +
            "and " +
            "   g.trim in (:trim) " +
            "and " +
            "   g.eduYear in (:eduYear) ")
    List<List<String>> findAllRetakenSubjectForStudentTrimEduYear(@Param("studentCode") Integer studentCode,
                                                                  @Param("trim") List<String> trim,
                                                                  @Param("eduYear") List<String> eduYear,
                                                                  Sort sort);

    List<StudentEntity> findAll();

    List<StudentEntity> findAllByStudentSurnameAndStudentNameAndStudentPatronymic(String surname, String name, String patronymic);

    @Query("select avg(vm.completeMark) " +
            "from VidomistMarkEntity vm " +
            "inner join VidomistEntity v on vm.vidomistMarkId.vidomistNo=v.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "where " +
            "   vm.vidomistMarkId.studentCode=:studentCode " +
            "and " +
            "  g.course in (:course) " +
            "and " +
            "   g.trim in (:trim) ")
    double findStudentAverageMarksForCourseTrim(@Param("studentCode") Integer studentCode,
                                                @Param("course") List<Integer> course,
                                                @Param("trim") List<String> trim);

    @Query("select sub.subjectName, sub.eduLevel,sub.faculty, vm.completeMark as completeMark " +
            "from VidomistMarkEntity vm " +
            "inner join VidomistEntity v on vm.vidomistMarkId.vidomistNo=v.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo=g.subject.subjectNo " +
            "where " +
            "   vm.vidomistMarkId.studentCode=:studentCode " +
            "and " +
            "  g.course in (:course) " +
            "and " +
            "   g.trim in (:trim) ")
    List<List<String>> findAllStudentMarks(@Param("studentCode") Integer studentCode,
                                           @Param("course") List<Integer> course,
                                           @Param("trim") List<String> trim,
                                           Sort sort);

    void deleteByStudentCode(int studentCode);

    @Query("select s.studentCode, s.studentName, s.studentName, s.studentPatronymic " +
            "from StudentEntity s " +
            "inner join VidomistMarkEntity  vm on s.studentCode=vm.vidomistMarkId.studentCode " +
            "inner join VidomistEntity v on vm.vidomistMarkId.studentCode=v.vidomistNo " +
            "inner join TutorEntity t on v.tutor.tutorNo=t.tutorNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo = g.subject.subjectNo " +
            "where " +
            "   g.eduYear in (:eduYear) " +
            "and " +
            "   g.groupName in (:groupName)" +
            "and " +
            "   g.trim in (:trim) " +
            "and " +
            "   g.course in (:course)" +
            "and " +
            "   sub.subjectName in (:subjectName)" +
            "and " +
            "   t.tutorNo in (:tutorNo) ")
    List<List<String>> findAllStudentByYearSubjectGroupTeacherTrimCourse(
            @Param("eduYear") List<String> eduYear,
            @Param("groupName") List<String> groupName,
            @Param("trim") List<String> trim,
            @Param("course") List<Integer> course,
            @Param("subjectName") List<String> subjectName,
            @Param("tutorNo") List<Integer> tutorNo,
            Sort sort);

//    @Query("select new com.project.database.additionalEntities.StudentMark( s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic, s.studentRecordBook, avg(vm.completeMark)) " +
    @Query("select s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic, s.studentRecordBook, avg(vm.completeMark) as completeMark " +
            "from StudentEntity s " +
            "inner join VidomistMarkEntity vm on s.studentCode=vm.vidomistMarkId.studentCode " +
            "inner join VidomistEntity v on vm.vidomistMarkId.studentCode=v.vidomistNo " +
            "inner join TutorEntity t on v.tutor.tutorNo=t.tutorNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo = g.subject.subjectNo " +
            "where " +
            "   g.eduYear in (:eduYear) " +
            "and " +
            "   g.groupName in (:groupName) " +
            "and " +
            "   g.trim in (:trim) " +
            "and " +
            "   g.course in (:course) " +
            "and " +
            "   sub.subjectName in (:subjectName) " +
            "and " +
            "   t.tutorNo in (:tutorNo) " +
            "group by s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic, s.studentRecordBook ")
    List<List<String>> findAllStudents(
            @Param("eduYear") List<String> eduYear,
            @Param("groupName") List<String> groupName,
            @Param("trim") List<String> trim,
            @Param("course") List<Integer> course,
            @Param("subjectName") List<String> subjectName,
            @Param("tutorNo") List<Integer> tutorNo,
            Sort sort);

    @Query("select s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic, s.studentRecordBook, avg(bme.completeMark) as completeMark  " +
            "from StudentEntity s " +
            "inner join BigunetsMarkEntity bme on bme.bigunetsMarkId.studentCode = s.studentCode " +
            "where exists (" +
            "   select vm " +
            "   from VidomistMarkEntity vm " +
            "   where vm.vidomistMarkId.studentCode =s.studentCode " +
            "       and vm.ectsMark='F' " +
            "       and lower(vm.natMark) not like '%недоп%' " +
            "       and not exists(" +
            "           select bm " +
            "           from BigunetsMarkEntity bm " +
            "           where bm.bigunetsMarkId.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "               and bm.bigunetsMarkId.studentCode=s.studentCode " +
            "           ) " +
            "   and vm.vidomistMarkId.vidomistNo in ( " +
            "       select v1.vidomistNo " +
            "       from VidomistEntity v1 " +
            "           inner join GroupEntity g on g.groupCode=v1.group.groupCode " +
            "           inner join SubjectEntity sub on g.subject.subjectNo=sub.subjectNo " +
            "       where " +
            "           g.eduYear in (:eduYear) " +
            "       and " +
            "           g.groupName in (:groupName) " +
            "       and " +
            "           g.trim in (:trim) " +
            "       and " +
            "           g.course in (:course) " +
            "       and " +
            "           sub.subjectName in (:subjectName) " +
            "       and " +
            "           v1.tutor.tutorNo in (:tutorNo) " +
            "   )" +
            ") group by s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic, s.studentRecordBook")
    List<List<String>> findAllDebtorsByYearSubjectGroupTeacherTrimCourse(
            @Param("eduYear") List<String> eduYear,
            @Param("groupName") List<String> groupName,
            @Param("trim") List<String> trim,
            @Param("course") List<Integer> course,
            @Param("subjectName") List<String> subjectName,
            @Param("tutorNo") List<Integer> tutorNo,
            Sort sort);

}
