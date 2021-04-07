package com.project.database.serviceHibernate;

import com.project.database.entities.StudentEntity;
import com.project.database.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceH {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * @param studentCode
     * @return StudentEntity(studentCode = 1, studentSurname = Кучерявий, studentName = Вадим, studentPatronymic = Юрійович, studentRecordBook = 13113)
     */
    public StudentEntity findByStudentCode(int studentCode) {
        return studentRepository.findByStudentCode(studentCode);
    }

    /**
     * @param recordBook
     * @return
     */
    public StudentEntity findByStudentRecordBook(String recordBook) {
        return studentRepository.findByStudentRecordBook(recordBook);
    }

    /**
     * @return [[Кучерявий, Вадим, Юрійович], [Синицин, Владислав, Олександрович],..]
     */
    public List<List<String>> findAllStudentNames() {
        return studentRepository.findAllStudentNames();
    }

    /**
     * @param name
     * @return [[Кучерявий, Вадим, Юрійович], [Рибак, Володимир, Ярославович]]
     */
    public List<List<String>> findAllStudentNames(String name) {
        return studentRepository.findAllStudentNames('%' + name + '%');
    }

    public List<List<String>> findAverageStudentsMarksTrimCourse(Integer semestr,
                                                                 Integer course,
                                                                 String eduYear,
                                                                 String sortBy,
                                                                 boolean sortDesc
    ) {
        String semestrStr = semestr == null
                ? " (select distinct(g.trim) from GroupEntity g) "
                : " (select distinct(g.trim) from GroupEntity g where g.trim = '" + semestr + "' ) ";

        String courseStr = course == null
                ? " select distinct(g.course) from GroupEntity g "
                : " select distinct(g.course) from GroupEntity g where g.course = " + course + " ";

        eduYear = eduYear == null
                ? " select distinct(g.eduYear) from GroupEntity g "
                : " select distinct(g.eduYear) from GroupEntity g where g.eduYear = '" + eduYear + "' ";

        String sortDescStr = sortDesc
                ? " DESC "
                : " ASC ";

        sortBy = sortBy.equals("student_surname")
                ? " s.studentSurname " + sortDescStr
                : " avg(vm.completeMark) " + sortDescStr;

        return studentRepository.findAverageStudentsMarksTrimCourse(" (select distinct g.trim from \"group\" g) ");

    }


}
