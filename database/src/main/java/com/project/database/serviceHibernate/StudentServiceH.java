package com.project.database.serviceHibernate;

import com.project.database.entities.GroupEntity;
import com.project.database.entities.StudentEntity;
import com.project.database.repository.GroupRepository;
import com.project.database.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceH {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;


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
        // TODO semester is {1, 2, 3} as {Осінь, Весна, Літо}. Map it to {1, 2, 2д, 3...}
        // For example:
        // course 2 semester 3 is 4д
        // course 3 semester 1 is 5
        List<String> semesters = semestr == null
                ? groupRepository.findAll() //            🔽🔽🔽🔽
                    .stream().map(GroupEntity::getTrim).distinct().collect(Collectors.toList())
                : groupRepository.findDistinctAllByTrimIn(Collections.singletonList(String.valueOf(semestr)))
                    .stream().map(GroupEntity::getTrim).distinct().collect(Collectors.toList());

        List<Integer> courses = course == null
                ? groupRepository.findAll()//              🔽🔽🔽🔽
                    .stream().map(GroupEntity::getCourse).distinct().collect(Collectors.toList())
                : groupRepository.findDistinctAllByCourseIn(Collections.singletonList(course))
                    .stream().map(GroupEntity::getCourse).distinct().collect(Collectors.toList());

        List<String> eduYears = eduYear == null
                ? groupRepository.findAll()
                    .stream().map(GroupEntity::getEduYear).distinct().collect(Collectors.toList())
                : groupRepository.findDistinctAllByEduYearIn(Collections.singletonList(eduYear))
                    .stream().map(GroupEntity::getEduYear).distinct().collect(Collectors.toList());

        String sortDescStr = sortDesc
                ? " DESC "
                : " ASC ";

        sortBy = sortBy.equals("student_surname")
                ? " s.studentSurname " + sortDescStr
                : " avg(vm.completeMark) " + sortDescStr;

        return studentRepository.findAverageStudentsMarksTrimCourse(semesters, courses, eduYears);

    }


}
