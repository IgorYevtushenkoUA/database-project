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
            "where gr.trim in (:trim) and gr.course in (select distinct(g.course) from GroupEntity g) and gr.eduYear " +
            "in (select distinct(g.eduYear) from GroupEntity g) " +
            "group by s.studentCode, s.studentSurname, s.studentName, s.studentPatronymic " +
            "order by s.studentSurname")
    List<List<String>> findAverageStudentsMarksTrimCourse(
            @Param("trim") String trim
    );

}
