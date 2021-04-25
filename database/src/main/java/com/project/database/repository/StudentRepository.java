package com.project.database.repository;

import com.project.database.entities.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer>, StudentRepositoryCustom {

    StudentEntity findByStudentCode(int studentCode);

    StudentEntity findByStudentRecordBook(String recordBook);

    @Query("select s.studentSurname, s.studentName, s.studentPatronymic from StudentEntity s")
    List<List<String>> findAllStudentNames();

    @Query("select s.studentSurname, s.studentName, s.studentPatronymic from StudentEntity s where lower(s.studentSurname) like:name or s.studentName like:name or s.studentPatronymic like:name")
    List<List<String>> findAllStudentNames(@Param("name") String name);

    @Query("select s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic,s.studentRecordBook, (sum(sub.credits * vm.completeMark) / sum(sub.credits)) as rating,gr.course as course, gr.trim  " +
            "from StudentEntity s inner join VidomistMarkEntity vm on s.studentCode=vm.vidomistMarkId.studentCode " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity gr on gr.groupCode=v.group.groupCode " +
            "inner join TutorEntity t on t.tutorNo=v.tutor.tutorNo " +
            "inner join SubjectEntity sub on sub.subjectNo=gr.subject.subjectNo " +
            "where lower(concat(s.studentSurname,' ', s.studentName, ' ', s.studentPatronymic)) like :pib " +
            "group by s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic,s.studentRecordBook, gr.course, gr.trim ")
    Page<Object[]> findAllStudentsByPIB(@Param("pib") String pib, Pageable pageable);

    @Query("select s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic,s.studentRecordBook, (sum(sub.credits * vm.completeMark) / sum(sub.credits)) as rating, gr.course as course, gr.trim  " +
            "from StudentEntity s inner join VidomistMarkEntity vm on s.studentCode=vm.vidomistMarkId.studentCode " +
            "inner join VidomistEntity v on v.vidomistNo=vm.vidomistMarkId.vidomistNo " +
            "inner join GroupEntity gr on gr.groupCode=v.group.groupCode " +
            "inner join TutorEntity t on t.tutorNo=v.tutor.tutorNo " +
            "inner join SubjectEntity sub on sub.subjectNo=gr.subject.subjectNo " +
            "where " +
            "   gr.eduYear in (:eduYears) " +
            "and " +
            "   sub.subjectName in (:subjectName) " +
            "and " +
            "   t.tutorNo in (:tutorNo) " +
            "and " +
            "   gr.groupName in (:groupName)" +
            "and " +
            "   gr.trim in (:semesters) " +
            "and " +
            "   gr.course in (:courses) " +
            "group by s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic,s.studentRecordBook, gr.course, gr.trim ")
    Page<Object[]> findStudentsWithRating(
            @Param("eduYears") List<String> eduYears,
            @Param("subjectName") List<String> subjectName,
            @Param("tutorNo") List<Integer> tutorNo,
            @Param("groupName") List<String> groupName,
            @Param("semesters") List<String> semesters,
            @Param("courses") List<Integer> courses,
            Pageable pageable
    );

    @Query(value = "select s.student_code,\n" +
            "       s.student_surname                                      as studentSurname,\n" +
            "       s.student_name,\n" +
            "       s.student_patronymic,\n" +
            "       s.student_record_book,\n" +
            "       (sum(sub.credits * vm.complete_mark) / sum(sub.credits)) as rating \n" +
            "from \"group\" gr\n" +
            "         inner join vidomist v on gr.group_code = v.group_code\n" +
            "         inner join vidomist_mark vm on v.vidomist_no = vm.vidomist_no\n" +
            "         inner join student s on s.student_code = vm.student_code\n" +
            "         inner join subject sub on gr.subject_no = sub.subject_no\n" +
            "group by s.student_code, s.student_surname, s.student_name, s.student_patronymic, s.student_record_book ", nativeQuery = true)
    Page<Object[]> findStudentsWithRatingDefault(Pageable pageable);

    @Query("select s.studentCode, s.studentRecordBook, s.studentSurname, s.studentName, s.studentPatronymic, gr.course as course, avg(vm.completeMark) as completeMark " +
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
            "group by s.studentCode,s.studentRecordBook, s.studentSurname, s.studentName, s.studentPatronymic, gr.course ")
    Page<Object[]> findAverageStudentMarksTrimCourse(
            @Param("studentCode") Integer studentCode,
            @Param("semesters") List<String> semesters,
            @Param("courses") List<Integer> courses,
            @Param("eduYears") List<String> eduYears,
            Pageable pageable
    );

    @Query(value = "select s.student_code,\n" +
            "       s.student_surname as studentSurname,\n" +
            "       s.student_name,\n" +
            "       s.student_patronymic,\n" +
            "       s.student_record_book,\n" +
            "       (sum(sub.credits * vm.complete_mark) / sum(sub.credits)) as rating\n"+
            "from \"group\" gr\n" +
            "         inner join vidomist v on gr.group_code = v.group_code\n" +
            "         inner join vidomist_mark vm on v.vidomist_no = vm.vidomist_no\n" +
            "         inner join student s on s.student_code = vm.student_code\n" +
            "         inner join bigunets_mark bme on bme.student_code = s.student_code\n" +
            "         inner join subject sub on gr.subject_no = sub.subject_no\n" +
            "where exists(\n" +
            "              select vm\n" +
            "              from vidomist_mark vm\n" +
            "              where vm.student_code = s.student_code\n" +
            "                and vm.ects_mark = 'F'\n" +
            "                and lower(vm.nat_mark) not like '%недоп%'\n" +
            "                and not exists(\n" +
            "                      select bm\n" +
            "                      from bigunets_mark bm\n" +
            "                      where bm.vidomist_no = vm.vidomist_no\n" +
            "                        and bm.student_code = s.student_code\n" +
            "                  )\n" +
            "                and vm.vidomist_no in (\n" +
            "                  select v1.vidomist_no\n" +
            "                  from vidomist v1\n" +
            "                           inner join \"group\" g on g.group_code = v1.group_code\n" +
            "                           inner join subject sub on g.subject_no = sub.subject_no\n" +
            "                  where g.edu_year in (:eduYear)\n" +
            "                    and g.group_name in (:groupName)\n" +
            "                    and g.trim in (:trim)\n" +
            "                    and g.course in (:course)\n" +
            "                    and sub.subject_name in (:subjectName)\n" +
            "                    and v1.tutor_no in (:tutorNo)\n" +
            "              )\n" +
            "          )\n" +
            "group by s.student_code, s.student_surname, s.student_name, s.student_patronymic, s.student_record_book "
            , nativeQuery = true)
    Page<Object[]> findDebtorsRatingDefault(
            @Param("eduYear") List<String> eduYear,
            @Param("subjectName") List<String> subjectName,
            @Param("tutorNo") List<Integer> tutorNo,
            @Param("groupName") List<String> groupName,
            @Param("trim") List<String> trim,
            @Param("course") List<Integer> course,
            Pageable pageable
    );

    @Query(value = "select s.student_code, s.student_surname,s.student_name, s.student_patronymic, s.student_record_book, gr.course as course, gr.trim  " +
            "from \"group\" gr " +
            "inner join vidomist v on gr.group_code = v.group_code " +
            "inner join vidomist_mark vm on v.vidomist_no = vm.vidomist_no " +
            "inner join student s on s.student_code = vm.student_code " +
            "inner join bigunets_mark bme on bme.student_code=s.student_code " +
            "where exists ( " +
            "   select vm " +
            "   from vidomist_mark vm " +
            "   where vm.student_code=s.student_code " +
            "       and vm.ects_mark='F' " +
            "       and lower(vm.nat_mark) not like '%недоп%' " +
            "       and not exists (" +
            "           select bm " +
            "           from bigunets_mark bm " +
            "           where bm.vidomist_no=vm.vidomist_no and bm.student_code=s.student_code" +
            "   )" +
            "   and vm.vidomist_no in (" +
            "   select v1.vidomist_no " +
            "   from vidomist v1 " +
            "   inner join \"group\" g on g.group_code=v1.group_code " +
            "   inner join subject sub on g.subject_no = sub.subject_no " +
            "   where g.edu_year in (:eduYear) " +
            "   and g.group_name in (:groupName) " +
            "   and g.trim in (:trim) " +
            "   and g.course in (:course) " +
            "   and sub.subject_name in (:subjectName) " +
            "   and v1.tutor_no in (:tutorNo) " +
            "   ) " +
            ")group by s.student_code, s.student_surname,s.student_name, s.student_patronymic, s.student_record_book, gr.course as course, gr.trim "
            , nativeQuery = true)
    Page<Object[]> findDebtorsRating(
            @Param("eduYear") List<String> eduYear,
            @Param("subjectName") List<String> subjectName,
            @Param("tutorNo") List<Integer> tutorNo,
            @Param("groupName") List<String> groupName,
            @Param("trim") List<String> trim,
            @Param("course") List<Integer> course,
            Pageable pageable
    );

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
    Page<Object[]> findAllWhoHasRetakeSubjectTrimEduYear(@Param("subjectName") List<String> subjectName,
                                                         @Param("trim") List<String> trim,
                                                         @Param("course") List<Integer> course,
                                                         @Param("eduYear") List<String> eduYear,
                                                         Pageable pageable);

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
    Page<Object[]> findAllRetakenSubjectForStudentTrimEduYear(@Param("studentCode") Integer studentCode,
                                                              @Param("trim") List<String> trim,
                                                              @Param("eduYear") List<String> eduYear,
                                                              Pageable pageable);

    Page<StudentEntity> findAll(Pageable pageable);

    Page<StudentEntity> findAllByStudentSurnameAndStudentNameAndStudentPatronymic(String surname, String name, String patronymic, Pageable pageable);

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
    Double findStudentAverageMarksForCourseTrim(@Param("studentCode") Integer studentCode,
                                                @Param("course") List<Integer> course,
                                                @Param("trim") List<String> trim);

    @Query("select avg(vm.completeMark) " +
            "from VidomistMarkEntity vm " +
            "inner join VidomistEntity v on vm.vidomistMarkId.vidomistNo=v.vidomistNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "where " +
            "   vm.vidomistMarkId.studentCode=:studentCode ")
    Double findStudentAverageMarksForCourseTrim(@Param("studentCode") Integer studentCode);

    // here todo
    @Query("select sub.subjectNo, sub.subjectName, t.tutorSurname, t.tutorName, t.tutorPatronymic, gr.groupName, v.controlType, v.examDate, s.studentCode, vm.completeMark " +
            "from StudentEntity s " +
            "inner join VidomistMarkEntity vm on s.studentCode=vm.vidomistMarkId.studentCode " +
            "inner join VidomistEntity v on vm.vidomistMarkId.studentCode=v.vidomistNo " +
            "inner join TutorEntity t on v.tutor.tutorNo=t.tutorNo " +
            "inner join GroupEntity gr on gr.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo = gr.subject.subjectNo " +
            "where " +
            "   vm.vidomistMarkId.studentCode=:studentCode " +
            "and " +
            "  gr.course in (:course) " +
            "and " +
            "   gr.trim in (:trim) ")
    Page<Object[]> findAllStudentMarks(@Param("studentCode") Integer studentCode,
                                       @Param("course") List<Integer> course,
                                       @Param("trim") List<String> trim,
                                       Pageable pageable);

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
    Page<Object[]> findAllStudentByYearSubjectGroupTeacherTrimCourse(
            @Param("eduYear") List<String> eduYear,
            @Param("groupName") List<String> groupName,
            @Param("trim") List<String> trim,
            @Param("course") List<Integer> course,
            @Param("subjectName") List<String> subjectName,
            @Param("tutorNo") List<Integer> tutorNo,
            Pageable pageable);

    @Query("select s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic, s.studentRecordBook " +
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
    Page<Object[]> findAllStudents(
            @Param("eduYear") List<String> eduYear,
            @Param("groupName") List<String> groupName,
            @Param("trim") List<String> trim,
            @Param("course") List<Integer> course,
            @Param("subjectName") List<String> subjectName,
            @Param("tutorNo") List<Integer> tutorNo,
            Pageable pageable);

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
            ") group by s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic, s.studentRecordBook ")
    Page<Object[]> findAllDebtorsByYearSubjectGroupTeacherTrimCourse(
            @Param("eduYear") List<String> eduYear,
            @Param("groupName") List<String> groupName,
            @Param("trim") List<String> trim,
            @Param("course") List<Integer> course,
            @Param("subjectName") List<String> subjectName,
            @Param("tutorNo") List<Integer> tutorNo,
            Pageable pageable);

    @Query("select distinct(g.course) " +
            "from GroupEntity g " +
            "inner join VidomistEntity v on v.group.groupCode=g.groupCode " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.vidomistNo=v.vidomistNo " +
            "inner join StudentEntity s on s.studentCode=vm.vidomistMarkId.studentCode " +
            "where s.studentCode=:studentCode " +
            "order by g.course desc ")
    List<Integer> findMaxStudentCourse(@Param("studentCode") Integer studentCode);

    @Query("select distinct(g.trim) " +
            "from GroupEntity g " +
            "inner join VidomistEntity v on v.group.groupCode=g.groupCode " +
            "inner join VidomistMarkEntity vm on vm.vidomistMarkId.vidomistNo=v.vidomistNo " +
            "inner join StudentEntity s on s.studentCode=vm.vidomistMarkId.studentCode " +
            "where s.studentCode=:studentCode " +
            "order by g.trim desc ")
    List<String> findMaxStudentTrim(@Param("studentCode") Integer studentCode);


    // return StudentSubjectShortInfo
    @Query("select sub.subjectNo, sub.subjectName, t.tutorSurname, t.tutorName, t.tutorPatronymic,g.groupName, v.controlType, v.examDate, s.studentCode, vm.completeMark " +
            "from StudentEntity s " +
            "inner join VidomistMarkEntity vm on s.studentCode=vm.vidomistMarkId.studentCode " +
            "inner join VidomistEntity v on vm.vidomistMarkId.studentCode=v.vidomistNo " +
            "inner join TutorEntity t on v.tutor.tutorNo=t.tutorNo " +
            "inner join GroupEntity g on g.groupCode=v.group.groupCode " +
            "inner join SubjectEntity sub on sub.subjectNo = g.subject.subjectNo " +
            "where " +
            "   s.studentCode=:studentCode " +
            "and " +
            "   g.course=:course " +
            "and " +
            "   g.trim=:trim " +
            "group by sub.subjectNo, sub.subjectName, t.tutorSurname, t.tutorName, t.tutorPatronymic,g.groupName, v.controlType, v.examDate, s.studentCode, vm.completeMark ")
    Page<Object[]> findStudentMarks(
            @Param("studentCode") Integer studentCode,
            @Param("course") Integer course,
            @Param("trim") String trim,
            Pageable pageable
    );

}
