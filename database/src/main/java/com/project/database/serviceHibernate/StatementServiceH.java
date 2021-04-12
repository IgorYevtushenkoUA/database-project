package com.project.database.serviceHibernate;

import com.project.database.dto.statement.StatementReport;
import com.project.database.dto.statement.info.StatementFooter;
import com.project.database.dto.statement.info.StatementHeader;
import com.project.database.dto.statement.info.StatementInfo;
import com.project.database.dto.statement.info.StatementStudent;
import com.project.database.dto.student.StudentShortInfo;
import com.project.database.entities.GroupEntity;
import com.project.database.entities.SubjectEntity;
import com.project.database.entities.TutorEntity;
import com.project.database.entities.VidomistEntity;
import com.project.database.parser.parserStatement.StatementParser;
import com.project.database.repository.GroupRepository;
import com.project.database.repository.SubjectRepository;
import com.project.database.repository.TutorRepository;
import com.project.database.repository.VidomistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatementServiceH {
    private final VidomistRepository vidomistRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final TutorRepository tutorRepository;
    public Optional<StatementInfo> getStatementInfo(int statementId) {
        System.out.println("tyt");
        return Optional.of(buildStatementInfo(statementId));
    }

    private StatementInfo buildStatementInfo(int statementId) {
//        Object[] header = vidomistRepository.getStatementHeader(statementId);
        Object[] footer = vidomistRepository.getStatementFooter(statementId);
        List<Object[]> students = vidomistRepository.getStatementStudent(statementId);

        StatementInfo statementInfo = new StatementInfo();
//        statementInfo.setStatementHeader(buildStatementHeader(header));
//        statementInfo.setStatementFooter(buildStatementFooter(footer));
//        statementInfo.setStatementStudents(buildListStatementStudent(students));

        return statementInfo;
    }

    private StatementHeader buildStatementHeader(Object[] header) {
        StatementHeader statementHeader = new StatementHeader();
        int index = 0;
        statementHeader.setStatementNo((Integer) header[index++]);
        statementHeader.setEduLevel((String) header[index++]);
        statementHeader.setFaculty((String) header[index++]);
        statementHeader.setCourse((Integer) header[index++]);
        statementHeader.setGroup((String) header[index++]);
        statementHeader.setSubjectName((String) header[index++]);
        statementHeader.setSemester((String) header[index++]);
        statementHeader.setCreditNumber("3");
        statementHeader.setControlType((String) header[index++]);
        statementHeader.setExamDate((LocalDate) header[index++]);
        statementHeader.setTutorFullName((String) header[index++] + " " + header[index++] + " " + header[index++]);
        statementHeader.setTutorPosition((String) header[index++]);
        statementHeader.setTutorAcademicStatus((String) header[index++]);

        return statementHeader;
    }

    private StatementFooter buildStatementFooter(Object[] footer) {
        StatementFooter statementFooter = new StatementFooter();
        int index = 0;
        statementFooter.setPresentCount((Integer) footer[index++]);
        statementFooter.setAbsentCount((Integer) footer[index++]);
        statementFooter.setRejectedCount((Integer) footer[index++]);
        return statementFooter;
    }

    private List<StatementStudent> buildListStatementStudent(List<Object[]> students) {
        List<StatementStudent> statementStudentList = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            StatementStudent statementStudent = new StatementStudent();
            Object[] obj = students.get(i);
            int index = 0;
            statementStudent.setStudentId((Integer) obj[index++]);
            statementStudent.setStudentPI((String) obj[index++] + " " + obj[index++]);
            statementStudent.setStudentPatronymic((String) obj[index++]);
            statementStudent.setStudentRecordBook((String) obj[index++]);
            statementStudent.setSemesterGrade((Integer) obj[index++]);
            statementStudent.setControlGrade((Integer) obj[index++]);
            statementStudent.setTotalGrade((Integer) obj[index++]);
            statementStudent.setNationalGrade((String) obj[index++]);
            statementStudent.setEctsGrade((String) obj[index++]);

            statementStudentList.add(statementStudent);
        }
        return statementStudentList;
    }


    public Integer saveStatement(StatementReport statementFileName) {
        return 12345;
    }

    VidomistEntity findByStatementNo(int statementNo) {
        return vidomistRepository.findByVidomistNo(statementNo);
    }


    public Page<Object[]> findAllStudentVidomosties(int studentCode, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistRepository.findAllStudentVidomosties(studentCode, pageable);
    }


    public void insertVidomist(VidomistEntity vidomist) {
        if (vidomistRepository.findByVidomistNo(vidomist.getVidomistNo()) == null) {
            vidomistRepository.save(vidomist);
        }
    }


    public void deleteVidomistById(int vidomistNo) {
        vidomistRepository.deleteById(vidomistNo);
    }


    public Page<VidomistEntity> findAllByTutorNo(Integer tutorNo, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistRepository.findAllByTutorNo(tutorNo, pageable);
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


    public StatementReport processStatement(MultipartFile file) throws FileAlreadyExistsException, IOException {
        final Path statementFolder = Paths.get("statement").toAbsolutePath().normalize();
        Files.createDirectories(statementFolder);
        Path targetLocation = statementFolder.resolve(Objects.requireNonNull(file.getOriginalFilename()));
//        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        file.transferTo(targetLocation);

        final StatementParser parser = new StatementParser();
        return parser.getStatementsReportByRoot(targetLocation);
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
            studentInfo.setStudentRating((Double) obj[index++]);
            studentInfo.setStudentCourse((Integer) obj[index++]);
            studentInfo.setStudentTrim((String) obj[index++]);
            students.add(studentInfo);
        }
        return new PageImpl<>(students, pageable, total);
    }


}
