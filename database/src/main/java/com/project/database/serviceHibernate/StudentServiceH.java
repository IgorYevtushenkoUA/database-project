package com.project.database.serviceHibernate;

import com.project.database.dto.student.StudentShortInfo;
import com.project.database.entities.GroupEntity;
import com.project.database.entities.StudentEntity;
import com.project.database.entities.SubjectEntity;
import com.project.database.entities.TutorEntity;
import com.project.database.repository.GroupRepository;
import com.project.database.repository.StudentRepository;
import com.project.database.repository.SubjectRepository;
import com.project.database.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
     * Знайти студента за Айді
     *
     * @param studentCode
     * @return StudentEntity(studentCode = 1, studentSurname = Кучерявий, studentName = Вадим, studentPatronymic = Юрійович, studentRecordBook = 13113)
     */
    public StudentEntity findByStudentCode(int studentCode) {
        return studentRepository.findByStudentCode(studentCode);
    }


    /**
     * Знайти студента за книгою студента
     *
     * @param recordBook
     * @return
     */
    public StudentEntity findByStudentRecordBook(String recordBook) {
        return studentRepository.findByStudentRecordBook(recordBook);
    }


    /**
     * Знайти всі імена студентів
     *
     * @return [[Кучерявий, Вадим, Юрійович], [Синицин, Владислав, Олександрович],..]
     */
    public List<List<String>> findAllStudentNames() {
        return studentRepository.findAllStudentNames();
    }


    /**
     * Знайти всі прізвища студентів від частини прізвища
     *
     * @param name
     * @return [[Кучерявий, Вадим, Юрійович], [Рибак, Володимир, Ярославович]]
     */
    public List<List<String>> findAllStudentNames(String name) {
        return studentRepository.findAllStudentNames('%' + name + '%');
    }

    /**
     * Знайти всі трими
     *
     * @param str
     * @return
     */
    public List<String> findTrims(String str) {
        return studentRepository.findTrims(str);
    }

    /**
     * Знайти Середню оцінку (РЕЙТИНГ) студентів за певний проміжок часу
     * МЕТОД ДЛЯ РЕЙТИНГУ (коли є хоча б 1 параметр)
     *
     * @param eduYear
     * @param semestr
     * @param course
     * @param sortBy
     * @param sortDesc
     * @return
     */
    public Page<StudentShortInfo> findStudentsWithRating(
            String eduYear, String subjectName, Integer tutorNo, String groupName, Integer semestr, Integer course,
            String sortBy, boolean sortDesc, int page, int numberPerPage
    ) {

        List<String> eduYearList = getEduYearsList(eduYear);
        List<String> subjectList = getSubjectList(subjectName);
        List<Integer> tutorList = getTutorList(tutorNo);
        List<String> groupList = getGroupList(groupName);
        List<String> semesterList = getSemestrList(semestr, semestrParser(course, semestr));
        List<Integer> courseList = getCourseList(course);
        Sort sort = setSort(sortBy, sortDesc);
        Pageable pageable = PageRequest.of(page - 1, numberPerPage, sort);

        Page<Object[]> pageList = studentRepository.findStudentsWithRating(
                eduYearList, subjectList, tutorList, groupList, semesterList, courseList, pageable);
        return buildStudentShortInfo(pageList, pageable, (int) pageList.getTotalElements());
    }

    public Page<StudentShortInfo> findStudentsWithRatingDefault(
            String sortBy, boolean sortDesc, int page, int numberPerPage) {
        Sort sort = setSort(sortBy, sortDesc);
        Pageable pageable = PageRequest.of(page - 1, numberPerPage, sort);
        Page<Object[]> pageList = studentRepository.findStudentsWithRatingDefault(pageable);
        return buildStudentShortInfo(pageList, pageable, (int) pageList.getTotalElements());
    }


    /**
     * Знайти середню оцінку(РЕЙТИНГ) студента
     * todo(add count average, teacher, group )
     *
     * @param studentCode
     * @param semestr
     * @param course
     * @param eduYear
     * @param sortBy
     * @param sortDesc
     * @return
     */
    public Page<Object[]> findAverageStudentMarksTrimCourse(
            Integer studentCode, Integer semestr, Integer course, String eduYear,
            String sortBy, boolean sortDesc, int page, int numberPerPage
    ) {
        List<String> semesters = getSemestrList(semestr, semestrParser(course, semestr));
        List<Integer> courses = getCourseList(course);
        List<String> eduYears = getEduYearsList(eduYear);
        Sort sort = setSort(sortBy, sortDesc);
        Pageable pageable = PageRequest.of(page - 1, numberPerPage, sort);
        return studentRepository.findAverageStudentMarksTrimCourse(studentCode, semesters, courses, eduYears, pageable);
    }

    /**
     * todo (тут проблеми із сортуванням, із Pageable мають вирішитися)
     * Знайти всіх боржників
     *
     * @param eduYear
     * @param groupName
     * @param trim
     * @param course
     * @param subjectName
     * @param tutorNo
     * @param sortBy
     * @param sortDesc
     * @return
     */
    public Page<StudentShortInfo> findDebtorsRatingDefault(
            String eduYear, String groupName, Integer trim, Integer course, String subjectName, Integer tutorNo,
            String sortBy, boolean sortDesc, int page, int numberPerPage
    ) {

        List<String> eduYearList = getEduYearsList(eduYear);
        List<String> groupList = getGroupList(groupName);
        List<Integer> courseList = getCourseList(course);
        List<String> trimList = getSemestrList(trim, semestrParser(course, trim));
        List<String> subjectList = getSubjectList(subjectName);
        List<Integer> tutorList = getTutorList(tutorNo);
        Sort sort = setSort(sortBy, sortDesc);
        Pageable pageable = PageRequest.of(page - 1, numberPerPage, sort);

        Page<Object[]> pageList = studentRepository.findDebtorsRatingDefault(
                eduYearList, subjectList, tutorList, groupList, trimList, courseList, pageable);
        return buildStudentShortInfo(pageList, pageable, (int) pageList.getTotalElements());
    }

    public List<String[]> findStudentMarks(Integer studentCode, Integer course, String trim) {
        return studentRepository.findStudentMarks(studentCode, course, trim);
    }

    /**
     * Знайти всіх боржників
     * todo(add count average, teacher, group )
     *
     * @param subjectName
     * @param semestr
     * @param course
     * @param eduYear
     * @param sortBy
     * @param sortDesc
     * @return
     */
    public Page<Object[]> findAllWhoHasRetakeSubjectTrimEduYear(
            String subjectName, Integer semestr, Integer course, String eduYear,
            String sortBy, boolean sortDesc, int page, int numberPerPage) {

        List<String> subjects = getSubjectList(subjectName);
        List<String> semesters = getSemestrList(semestr, semestrParser(course, semestr));
        List<Integer> courses = getCourseList(course);
        List<String> eduYears = getEduYearsList(eduYear);
        Sort sort = setSort(sortBy, sortDesc);
        Pageable pageable = PageRequest.of(page - 1, numberPerPage, sort);
        return studentRepository.findAllWhoHasRetakeSubjectTrimEduYear(subjects, semesters, courses, eduYears, pageable);
    }

    /**
     * Знайти ВСІ борги для певного студента
     *
     * @param studentCode
     * @param trim
     * @param eduYear
     * @param sortBy
     * @param sortDesc
     * @return
     */
    public Page<Object[]> findAllRetakenSubjectForStudentTrimEduYear(
            Integer studentCode, Integer trim, String eduYear,
            String sortBy, boolean sortDesc, int page, int numberPerPage) {

        List<String> trimList = getSemestrList(trim, semestrParser(null, trim));
        List<String> eduYearList = getEduYearsList(eduYear);
        Sort sort = setSort(sortBy, sortDesc);
        Pageable pageable = PageRequest.of(page - 1, numberPerPage, sort);
        return studentRepository.findAllRetakenSubjectForStudentTrimEduYear(studentCode, trimList, eduYearList, pageable);
    }


    public Page<StudentEntity> findAll(String sortBy, boolean sortDesc, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage, setSort(sortBy, sortDesc));
        return studentRepository.findAll(pageable);
    }

    /**
     * Знайти всіх студентів за (прізвище, ім'я, по-батькові)
     *
     * @param surname
     * @param name
     * @param patronymic
     * @return
     */
    public Page<StudentEntity> findAllByStudentSurnameAndStudentNameAndStudentPatronymic(
            String surname, String name, String patronymic,
            String sortBy, boolean sortDesc, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage, setSort(sortBy, sortDesc));
        return studentRepository.findAllByStudentSurnameAndStudentNameAndStudentPatronymic(
                surname, name, patronymic, pageable);
    }

    /**
     * Знайти середню оцінку(РЕЙТИНГ) студента за певний період
     *
     * @param studentCode
     * @param course
     * @param trim
     * @return
     */
    public double findStudentAverageMarksForCourseTrim(Integer studentCode, Integer course, Integer trim) {

        List<Integer> courseList = getCourseList(course);
        List<String> trimList = getSemestrList(trim, semestrParser(course, trim));
        return studentRepository.findStudentAverageMarksForCourseTrim(studentCode, courseList, trimList);
    }

    /**
     * найти всі ПРЕДМЕТ-ОЦІНКА студента за певний курс та триместр
     *
     * @param studentCode
     * @param course
     * @param trim
     * @param sortBy
     * @param sortDesc
     * @return
     */
    public Page<Object[]> findAllStudentMarks(
            Integer studentCode, Integer course, Integer trim,
            String sortBy, boolean sortDesc, int page, int numberPerPage) {
        List<Integer> courseList = getCourseList(course);
        List<String> trimList = getSemestrList(trim, semestrParser(course, trim));
        Sort sort = setSort(sortBy, sortDesc);
        Pageable pageable = PageRequest.of(page - 1, numberPerPage, sort);

        return studentRepository.findAllStudentMarks(studentCode, courseList, trimList, pageable);
    }

    public void deleteByStudentCode(int studentCode) {
        studentRepository.deleteByStudentCode(studentCode);
    }

    /**
     * Знайти всіх СТУДЕНТІВ, за певний курс, групу, викладача, тощо
     *
     * @param eduYear
     * @param groupName
     * @param trim
     * @param course
     * @param subjectName
     * @param tutorNo
     * @param sortBy
     * @param sortDesc
     * @return
     */
    public Page<Object[]> findAllStudentByYearSubjectGroupTeacherTrimCourse(
            String eduYear, String groupName, Integer trim, Integer course, String subjectName, Integer tutorNo,
            String sortBy, boolean sortDesc, int page, int numberPerPage) {
        List<String> eduYearList = getEduYearsList(eduYear);
        List<String> groupList = getGroupList(groupName);
        List<String> trimList = getSemestrList(trim, semestrParser(course, trim));
        List<Integer> courseList = getCourseList(course);
        List<String> subjectNameList = getSubjectList(subjectName);
        List<Integer> tutorList = getTutorList(tutorNo);
        Sort sort = setSort(sortBy, sortDesc);
        Pageable pageable = PageRequest.of(page - 1, numberPerPage, sort);

        return studentRepository.findAllStudentByYearSubjectGroupTeacherTrimCourse(
                eduYearList, groupList, trimList, courseList, subjectNameList, tutorList, pageable);
    }

    public Page<Object[]> findAllStudents(
            String eduYear, String groupName, Integer trim, Integer course, String subjectName, Integer tutorNo,
            String sortBy, boolean sortDesc, int page, int numberPerPage) {
        List<String> eduYearList = getEduYearsList(eduYear);
        List<String> groupList = getGroupList(groupName);
        List<String> trimList = getSemestrList(trim, semestrParser(course, trim));
        List<Integer> courseList = getCourseList(course);
        List<String> subjectNameList = getSubjectList(subjectName);
        List<Integer> tutorList = getTutorList(tutorNo);
        Sort sort = setSort(sortBy, sortDesc);
        Pageable pageable = PageRequest.of(page - 1, numberPerPage, sort);

        return studentRepository.findAllStudents(
                eduYearList, groupList, trimList, courseList, subjectNameList, tutorList, pageable);
    }

    /**
     * Знайти всіх боржників todo (add rating)
     *
     * @param eduYear
     * @param groupName
     * @param trim
     * @param course
     * @param subjectName
     * @param tutorNo
     * @param sortBy
     * @param sortDesc
     * @return
     */
    public Page<Object[]> findAllDebtorsByYearSubjectGroupTeacherTrimCourse(
            String eduYear, String groupName, Integer trim, Integer course, String subjectName, Integer tutorNo,
            String sortBy, boolean sortDesc, int page, int numberPerPage) {
        List<String> eduYearList = getEduYearsList(eduYear);
        List<String> groupList = getGroupList(groupName);
        List<Integer> courseList = getCourseList(course);
        List<String> trimList = getSemestrList(trim, semestrParser(course, trim));
        List<String> subjectNameList = getSubjectList(subjectName);
        List<Integer> tutorList = getTutorList(tutorNo);
        Sort sort = setSort(sortBy, sortDesc);
        Pageable pageable = PageRequest.of(page - 1, numberPerPage, sort);
        return studentRepository.findAllDebtorsByYearSubjectGroupTeacherTrimCourse(
                eduYearList, groupList, trimList, courseList, subjectNameList, tutorList, pageable);
    }

    /**
     * Знайти максимальний курс для студента
     *
     * @param studentCode
     * @return
     */
    public Integer findMaxStudentCourse(Integer studentCode) {
        return studentRepository.findMaxStudentCourse(studentCode).stream().findFirst().orElse(null);
    }

    /**
     * Знайти максимальний триместр для студента
     *
     * @param studentCode
     * @return
     */
    public String findMaxStudentTrim(Integer studentCode) {
        return studentRepository.findMaxStudentTrim(studentCode).stream().findFirst().orElse(null);
    }


    /**
     * insert student
     *
     * @param student
     */
    public void insertStudent(StudentEntity student) {
        if (studentRepository.findByStudentRecordBook(student.getStudentRecordBook()) == null)
            studentRepository.save(student);
    }

    /**
     * delete by id
     *
     * @param studentCode
     */
    public void deleteStudentById(int studentCode) {
        studentRepository.deleteById(studentCode);
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
                ? groupRepository.findAll()
                .stream().map(GroupEntity::getTrim).distinct().collect(Collectors.toList())
                : groupRepository.findDistinctAllByTrimIn(semestrList)
                .stream().map(GroupEntity::getTrim).distinct().collect(Collectors.toList());

    }

    private List<Integer> getCourseList(Integer course) {
        return course == null
                ? groupRepository.findAll()
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
        sortBy = setSortBy(sortBy);
        return sortDesc
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
    }

    private String setSortBy(String sortBy) {

        switch (sortBy) {
            case "surname":
                return "studentSurname";
            case "completeMark":
                return "completeMark";
            case "course":
                return "course";
            default:
                return "studentSurname";
        }
    }

    private Page<StudentShortInfo> buildStudentShortInfo(Page<Object[]> studentsP, Pageable pageable, int total) {
        List<StudentShortInfo> students = new ArrayList<>();
        List<Object[]> list = studentsP.getContent();
        for (int i = 0; i < studentsP.getNumberOfElements(); i++) {
            StudentShortInfo studentInfo = new StudentShortInfo();
            Object[] obj = list.get(i);
            int index = 0;
            studentInfo.setStudentId((Integer) obj[index++]);
            studentInfo.setStudentSurname((String) obj[index++]);
            studentInfo.setStudentName((String) obj[index++]);
            studentInfo.setStudentPatronymic((String) obj[index++]);
            studentInfo.setStudentRecordBook((String) obj[index++]);
            studentInfo.setStudentRating((BigDecimal) obj[index++]);
            studentInfo.setStudentCourse((Integer) obj[index++]);
            studentInfo.setStudentTrim((String) obj[index++]);
            students.add(studentInfo);
        }
        return new PageImpl<>(students, pageable, total);
    }

}
