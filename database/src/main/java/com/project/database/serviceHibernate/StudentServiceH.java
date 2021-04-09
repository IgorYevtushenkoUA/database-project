package com.project.database.serviceHibernate;

import com.project.database.entities.GroupEntity;
import com.project.database.entities.StudentEntity;
import com.project.database.entities.SubjectEntity;
import com.project.database.entities.TutorEntity;
import com.project.database.repository.GroupRepository;
import com.project.database.repository.StudentRepository;
import com.project.database.repository.SubjectRepository;
import com.project.database.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final SubjectRepository subjectRepository;
    private final TutorRepository tutorRepository;

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

    public List<String> findTrims(String str) {
        return studentRepository.findTrims(str);
    }


    public Page<List<String>> findAverageStudentsMarksTrimCourse(Integer semestr,
                                                                 Integer course,
                                                                 String eduYear,
                                                                 String sortBy,
                                                                 boolean sortDesc,
                                                                 Pageable pageable
    ) {
        List<String> list = semestrParser(course, semestr);

        List<String> semesters = semestr == null
                ? groupRepository.findAll() //            🔽🔽🔽🔽
                .stream().map(GroupEntity::getTrim).distinct().collect(Collectors.toList())
                : groupRepository.findDistinctAllByTrimIn(list)
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

        Sort sort = setSort(sortBy, sortDesc);

        return studentRepository.findAverageStudentsMarksTrimCourse(semesters, courses, eduYears, sort, pageable);
    }

    public Page<List<String>> findAverageStudentMarksTrimCourse(Integer studentCode,
                                                                Integer semestr,
                                                                Integer course,
                                                                String eduYear,
                                                                String sortBy,
                                                                boolean sortDesc,
                                                                Pageable pageable
    ) {

        List<String> semesters = getSemestrList(semestr, semestrParser(course, semestr));

        List<Integer> courses = getCourseList(course);

        List<String> eduYears = getEduYearsList(eduYear);

        Sort sort = setSort(sortBy, sortDesc);

        return studentRepository.findAverageStudentMarksTrimCourse(studentCode, semesters, courses, eduYears, sort, pageable);
    }


    public Page<List<String>> findAllWhoHasRetakeSubjectTrimEduYear(String subjectName,
                                                                    Integer semestr,
                                                                    Integer course,
                                                                    String eduYear,
                                                                    String sortBy,
                                                                    boolean sortDesc,
                                                                    Pageable pageable) {

        List<String> subjects = getSubjectList(subjectName);

        List<String> semesters = getSemestrList(semestr, semestrParser(course, semestr));

        List<Integer> courses = getCourseList(course);

        List<String> eduYears = getEduYearsList(eduYear);

        Sort sort = setSort(sortBy, sortDesc);

        return studentRepository.findAllWhoHasRetakeSubjectTrimEduYear(subjects, semesters, courses, eduYears, sort, pageable);
    }

    public Page<List<String>> findAllRetakenSubjectForStudentTrimEduYear(Integer studentCode,
                                                                         Integer trim,
                                                                         String eduYear,
                                                                         String sortBy,
                                                                         boolean sortDesc,
                                                                         Pageable pageable) {

        List<String> trimList = getSemestrList(trim, semestrParser(null, trim));

        List<String> eduYearList = getEduYearsList(eduYear);

        Sort sort = setSort(sortBy, sortDesc);

        return studentRepository.findAllRetakenSubjectForStudentTrimEduYear(studentCode, trimList, eduYearList, sort, pageable);
    }

    public Page<StudentEntity> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public Page<StudentEntity> findAllByStudentSurnameAndStudentNameAndStudentPatronymic(String surname, String name, String patronymic, Pageable pageable) {
        return studentRepository.findAllByStudentSurnameAndStudentNameAndStudentPatronymic(surname, name, patronymic, pageable);
    }

    public double findStudentAverageMarksForCourseTrim(Integer studentCode, Integer course, Integer trim) {

        List<Integer> courseList = getCourseList(course);

        List<String> trimList = getSemestrList(trim, semestrParser(course, trim));

        return studentRepository.findStudentAverageMarksForCourseTrim(studentCode, courseList, trimList);
    }

    public Page<List<String>> findAllStudentMarks(Integer studentCode, Integer course, Integer trim, String sortBy, boolean sortDesc, Pageable pageable) {

        List<Integer> courseList = getCourseList(course);
        List<String> trimList = getSemestrList(trim, semestrParser(course, trim));

        Sort sort = setSort(sortBy, sortDesc);

        return studentRepository.findAllStudentMarks(studentCode, courseList, trimList, sort, pageable);
    }

    public void deleteByStudentCode(int studentCode) {
        studentRepository.deleteByStudentCode(studentCode);
    }

    public Page<List<String>> findAllStudentByYearSubjectGroupTeacherTrimCourse(String eduYear,
                                                                                String groupName,
                                                                                Integer trim,
                                                                                Integer course,
                                                                                String subjectName,
                                                                                Integer tutorNo,
                                                                                String sortBy,
                                                                                boolean sortDesc,
                                                                                Pageable pageable) {
        List<String> eduYearList = getEduYearsList(eduYear);
        List<String> groupList = getGroupList(groupName);
        List<String> trimList = getSemestrList(trim, semestrParser(course, trim));
        List<Integer> courseList = getCourseList(course);
        List<String> subjectNameList = getSubjectList(subjectName);
        List<Integer> tutorList = getTutorList(tutorNo);

        Sort sort = setSort(sortBy, sortDesc);

        List<List<String>> result = studentRepository.findAllStudentByYearSubjectGroupTeacherTrimCourse(
                eduYearList, groupList, trimList, courseList, subjectNameList, tutorList, sort, pageable)
                .stream().distinct().collect(Collectors.toList());

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), result.size());
        final Page<List<String>> page = new PageImpl<>(result.subList(start, end), pageable, result.size());

        return page;
    }

    public Page<List<String>> findAllStudents(String eduYear,
                                              String groupName,
                                              Integer trim,
                                              Integer course,
                                              String subjectName,
                                              Integer tutorNo,
                                              String sortBy,
                                              boolean sortDesc,
                                              Pageable pageable) {
        List<String> eduYearList = getEduYearsList(eduYear);
        List<String> groupList = getGroupList(groupName);
        List<String> trimList = getSemestrList(trim, semestrParser(course, trim));
        List<Integer> courseList = getCourseList(course);
        List<String> subjectNameList = getSubjectList(subjectName);
        List<Integer> tutorList = getTutorList(tutorNo);

        Sort sort = setSort(sortBy, sortDesc);

        List<List<String>> result = studentRepository.findAllStudents(
                eduYearList, groupList, trimList, courseList, subjectNameList, tutorList, sort, pageable)
                .stream().distinct().collect(Collectors.toList());

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), result.size());
        final Page<List<String>> page = new PageImpl<>(result.subList(start, end), pageable, result.size());

        return page;
    }

    public Page<List<String>> findAllDebtorsByYearSubjectGroupTeacherTrimCourse(String eduYear,
                                                                                String groupName,
                                                                                Integer trim,
                                                                                Integer course,
                                                                                String subjectName,
                                                                                Integer tutorNo,
                                                                                String sortBy,
                                                                                boolean sortDesc,
                                                                                Pageable pageable) {
        List<String> eduYearList = getEduYearsList(eduYear);
        List<String> groupList = getGroupList(groupName);
        List<String> trimList = getSemestrList(trim, semestrParser(course, trim));
        List<Integer> courseList = getCourseList(course);
        List<String> subjectNameList = getSubjectList(subjectName);
        List<Integer> tutorList = getTutorList(tutorNo);
        Sort sort = setSort(sortBy, sortDesc);
        List<List<String>> result = studentRepository.findAllDebtorsByYearSubjectGroupTeacherTrimCourse(
                eduYearList, groupList, trimList, courseList, subjectNameList, tutorList, sort, pageable)
                .stream().distinct().collect(Collectors.toList());

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), result.size());
        final Page<List<String>> page = new PageImpl<>(result.subList(start, end), pageable, result.size());

        return page;
    }

    private List<String> getSubjectList(String subjectName) {
        return subjectName == null
                ? subjectRepository.findAll()
                .stream().map(SubjectEntity::getSubjectName).distinct().collect(Collectors.toList())
                : subjectRepository.findDistinctBySubjectNameIn(Collections.singletonList(subjectName))
                .stream().map(SubjectEntity::getSubjectName).distinct().collect(Collectors.toList());
    }

    private List<String> getSemestrList(Integer semestr, List<String> semestrList) {
        return semestr == null
                ? groupRepository.findAll() //            🔽🔽🔽🔽
                .stream().map(GroupEntity::getTrim).distinct().collect(Collectors.toList())
                : groupRepository.findDistinctAllByTrimIn(semestrList)
                .stream().map(GroupEntity::getTrim).distinct().collect(Collectors.toList());

    }

    private List<Integer> getCourseList(Integer course) {
        return course == null
                ? groupRepository.findAll()//              🔽🔽🔽🔽
                .stream().map(GroupEntity::getCourse).distinct().collect(Collectors.toList())
                : groupRepository.findDistinctAllByCourseIn(Collections.singletonList(course))
                .stream().map(GroupEntity::getCourse).distinct().collect(Collectors.toList());


    }

    private List<String> getEduYearsList(String eduYear) {
        return eduYear == null
                ? groupRepository.findAll()
                .stream().map(GroupEntity::getEduYear).distinct().collect(Collectors.toList())
                : groupRepository.findDistinctAllByEduYearIn(Collections.singletonList(eduYear))
                .stream().map(GroupEntity::getEduYear).distinct().collect(Collectors.toList());
    }

    private List<String> getGroupList(String groupName) {
        return groupName == null
                ? groupRepository.findAll()
                .stream().map(GroupEntity::getGroupName).distinct().collect(Collectors.toList())
                : groupRepository.findDistinctAllByGroupNameIn(Collections.singletonList(groupName))
                .stream().map(GroupEntity::getGroupName).distinct().collect(Collectors.toList());
    }

    private List<Integer> getTutorList(Integer tutorNo) {
        return tutorNo == null
                ? tutorRepository.findAll()
                .stream().map(TutorEntity::getTutorNo).distinct().collect(Collectors.toList())
                : tutorRepository.findDistinctByTutorNoIn(Collections.singletonList(tutorNo))
                .stream().map(TutorEntity::getTutorNo).distinct().collect(Collectors.toList());
    }

    private List<String> semestrParser(Integer course, Integer semestr) {
        if (course != null) {
            if (semestr != null) {
                if (semestr.equals(3))
                    return List.of(course * 2 + "д");
                return semestr == 1
                        ? List.of(String.valueOf(course * semestr - 1))
                        : List.of(String.valueOf(course * semestr));
            } else {
                return List.of(String.valueOf(course * 2 - 1), String.valueOf(course * 2), String.valueOf(course * 2) + "д");
            }
        } else {
            if (semestr != null) {
                if (semestr == 3)
                    return List.of("2д", "4д", "6д", "8д");
                return semestr == 1
                        ? List.of("1", "3", "5", "7")
                        : List.of("2", "4", "6", "8");
            } else {
                return List.of("1", "2", "2д", "3", "4", "4д", "5", "6", "6д", "7", "8", "8д");
            }
        }
    }

    private Sort setSort(String sortBy, boolean sortDesc) {
        sortBy = sortBy.equals("student_surname")
                ? "studentSurname"
                : "completeMark";
        return sortDesc
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
    }

}
