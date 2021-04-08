package com.project.database.repository;

import com.project.database.entities.StudentEntity;
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

    @Query("select s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic, avg(vm.completeMark) " +
            "from StudentEntity s inner join VidomistMarkEntity vm on s.studentCode=vm.vidomistMarkId.studentCode " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity gr on gr.groupCode=v.group.groupCode " +
            "where " +
            "   gr.trim in (:semesters) " +
            "and " +
            "   gr.course in (:courses) " +
            "and " +
            "   gr.eduYear in (:eduYears) " +
            "group by s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic " +
            "order by s.studentSurname ASC")
    List<List<String>> findAverageStudentsMarksTrimCourse(
            @Param("semesters") List<String> semesters,
            @Param("courses") List<Integer> courses,
            @Param("eduYears") List<String> eduYears
    );

    @Query("select s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic, avg(vm.completeMark) " +
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
            "group by s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic " +
            "order by s.studentSurname ASC")
    List<List<String>> findAverageStudentMarksTrimCourse(
            @Param("studentCode") Integer studentCode,
            @Param("semesters") List<String> semesters,
            @Param("courses") List<Integer> courses,
            @Param("eduYears") List<String> eduYears
    );

    @Query("select s.studentSurname, s.studentName, s.studentPatronymic, s2.subjectName, bm.completeMark " +
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
            "   g.eduYear in (:eduYear) " +
            "order by s.studentSurname")
        // todo offset, limit, order by
    List<List<String>> findAllWhoHasRetakeSubjectTrimEduYear(@Param("subjectName") List<String> subjectName,
                                                             @Param("trim") List<String> trim,
                                                             @Param("course") List<Integer> course, @Param("eduYear") List<String> eduYear);

    @Query("select s.studentSurname, s.studentName, s.studentPatronymic, s2.subjectName, bm.completeMark " +
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
            "   g.eduYear in (:eduYear) " +
            "order by s.studentSurname")
        // todo offset, limit, order by
    List<List<String>> findAllRetakenSubjectForStudentTrimEduYear(@Param("studentCode") Integer studentCode,
                                                                  @Param("trim") List<String> trim,
                                                                  @Param("eduYear") List<String> eduYear);

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

    @Query("select sub.subjectName, sub.eduLevel,sub.faculty, vm.completeMark " +
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
                                           @Param("trim") List<String> trim);



}
