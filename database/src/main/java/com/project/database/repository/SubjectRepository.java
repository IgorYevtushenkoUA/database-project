package com.project.database.repository;

import com.project.database.entities.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {

    void deleteBySubjectNo(int subjectId);

    Page<SubjectEntity> findAll(Pageable pageable);

    @Query("select s.subjectName from SubjectEntity s order by s.subjectName")
    List<String> findAllSubjectNames();

    @Query("select s.subjectName from SubjectEntity s where s.subjectName like:name order by s.subjectName")
    List<String> findAllSubjectNames(@Param("name") String name);

    List<SubjectEntity> findDistinctBySubjectNameIn(@Param("subjectName") List<String> subjectName);

    SubjectEntity findBySubjectName(String subjectName);

    Page<Object[]> findAllBySubjectNameLike(String subjectName, Pageable pageable);

    @Query(value = "select sub.subject_no, subject_name, t.tutor_surname, t.tutor_name, t.tutor_patronymic, avg(vm.complete_mark)\n" +
            "from student s\n" +
            "         inner join vidomist_mark vm on s.student_code = vm.student_code\n" +
            "         inner join vidomist v on v.vidomist_no = vm.vidomist_no\n" +
            "         inner join vidomist_mark m on v.vidomist_no = m.vidomist_no\n" +
            "         inner join \"group\" g on g.group_code = v.group_code\n" +
            "         inner join subject sub on sub.subject_no = g.subject_no\n" +
            "         inner join tutor t on v.tutor_no=t.tutor_no\n" +
            "where exists(\n" +
            "              select sub.subject_no, s.student_code, s.student_surname, vm.complete_mark\n" +
            "              from student s1\n" +
            "                       inner join vidomist_mark vm1 on s1.student_code = vm1.student_code\n" +
            "                       inner join vidomist v1 on v1.vidomist_no = vm1.vidomist_no\n" +
            "                       inner join vidomist_mark m1 on v1.vidomist_no = m1.vidomist_no\n" +
            "                       inner join \"group\" g1 on g1.group_code = v1.group_code\n" +
            "                       inner join subject sub1 on sub1.subject_no = g1.subject_no\n" +
            "              where sub.subject_no = sub1.subject_no\n" +
            "          )\n" +
            "group by sub.subject_no, subject_name, t.tutor_surname, t.tutor_name, t.tutor_patronymic ", nativeQuery = true)
    Page<Object[]> findSubjectAverageMark(Pageable pageable);


}
