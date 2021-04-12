package com.project.database.serviceHibernate;

import com.project.database.dto.statement.shortInfo.StatementShortInfo;
import com.project.database.dto.statement.shortInfo.StudentsCount;
import com.project.database.dto.student.StudentShortInfo;
import com.project.database.entities.GroupEntity;
import com.project.database.entities.SubjectEntity;
import com.project.database.entities.TutorEntity;
import com.project.database.entities.VidomistEntity;
import com.project.database.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VidomistServiceH {
    private final VidomistRepository vidomistRepository;

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final TutorRepository tutorRepository;

    public Page<StatementShortInfo> findAllStudentVidomosties(int studentCode, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        Page<Object[]> pageList = vidomistRepository.findAllStudentVidomosties(studentCode, pageable);
        return buildStatementShortInfo(pageList, pageable, (int) pageList.getTotalElements());
    }

    private Page<StatementShortInfo> buildStatementShortInfo(Page<Object[]> vidommistP, Pageable pageable, int total) {
        List<StatementShortInfo> statementShortInfo = new ArrayList<>();
        List<Object[]> list = vidommistP.getContent();
        for (int i = 0; i < vidommistP.getNumberOfElements(); i++) {
            StatementShortInfo ssi = new StatementShortInfo();
            Object[] obj = list.get(i);
            int index = 0;
            ssi.setStatementNo((Integer) obj[index++]);
            ssi.setTutorFullName((String) obj[index++] + " " + obj[index++] + " " + obj[index++]);
            ssi.setSubjectName((String) obj[index++]);
            ssi.setGroup((String) obj[index++]);
            ssi.setControlType((String) obj[index++]);
            StudentsCount studentsCount = new StudentsCount();
            studentsCount.setPresentCount((Integer) obj[index++]);
            studentsCount.setAbsentCount((Integer) obj[index++]);
            studentsCount.setRejectedCount((Integer) obj[index++]);
            ssi.setStudentsCount(studentsCount);
            ssi.setExamDate((LocalDate) obj[index++]);

            statementShortInfo.add(ssi);
        }
        return new PageImpl<>(statementShortInfo, pageable, total);
    }

    public void insertVidomist(VidomistEntity vidomist) {
        if (vidomistRepository.findByVidomistNo(vidomist.getVidomistNo()) == null) {
            vidomistRepository.save(vidomist);
        }
    }

    public void deleteVidomistById(int vidomistNo) {
        vidomistRepository.deleteById(vidomistNo);
    }

    public Page<StatementShortInfo> findAllStatements(
            String eduYear, String subjectName, String tutorName, String groupName, Integer semestr, Integer course,
            String sortBy, boolean sortDesc, int page, int numberPerPage) {
        List<String> eduYearList = getEduYearsList(eduYear);
        List<String> subjectList = getSubjectList(subjectName);
        List<Integer> tutorList = getTutorList(tutorName);
        List<String> groupList = getGroupList(groupName);
        List<String> semesterList = getSemestrList(semestr, semestrParser(course, semestr));
        List<Integer> courseList = getCourseList(course);
//        Sort sort = setSort(sortBy, sortDesc);
        System.out.println(subjectList);
        System.out.println(tutorList);
        System.out.println(groupList);
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        Page<Object[]> pageList = vidomistRepository.findAllStatements(eduYearList, subjectList, tutorList, groupList, semesterList, courseList, pageable);
        return buildStatementShortInfo(pageList, pageable, (int) pageList.getTotalElements());
    }

    public Page<VidomistEntity> findAllBySubjectNo(Integer subjectNo, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistRepository.findAllBySubjectNo(subjectNo, pageable);
    }

    public Page<VidomistEntity> findAllByGroupName(String groupName, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistRepository.findAllByGroupName(groupName, pageable);
    }

    public int nonAdmissionByGroupCode(Integer groupCode) {
        return vidomistRepository.nonAdmissionByGroupCode(groupCode);
    }

    public int nonAdmissionBySubjectCode(Integer subjectNo) {
        return vidomistRepository.nonAdmissionBySubjectCode(subjectNo);
    }

    public int nonAdmissionByTeacherNo(Integer tutorNo) {
        return vidomistRepository.nonAdmissionByTeacherNo(tutorNo);
    }

    public VidomistEntity findVidomistNoByStudentRecordBookAndSubjectName(
            String studentRecordBook, String subjectName) {
        return vidomistRepository.findVidomistNoByStudentRecordBookAndSubjectName(studentRecordBook, subjectName);
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

    private List<Integer> getTutorList(String tutorName) {
        return tutorName == null
                ? tutorRepository.findAll()
                .stream().map(TutorEntity::getTutorNo).distinct().collect(Collectors.toList())
                : tutorRepository.findAllTutorsByFullName(tutorName);
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
            studentInfo.setStudentRating((Double) obj[index++]);
            studentInfo.setStudentCourse((Integer) obj[index++]);
            studentInfo.setStudentTrim((String) obj[index++]);
            students.add(studentInfo);
        }
        return new PageImpl<>(students, pageable, total);
    }


}
