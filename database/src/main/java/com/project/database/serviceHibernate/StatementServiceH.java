package com.project.database.serviceHibernate;

import com.project.database.dto.statement.StatementReport;
import com.project.database.dto.statement.info.StatementFooter;
import com.project.database.dto.statement.info.StatementHeader;
import com.project.database.dto.statement.info.StatementInfo;
import com.project.database.dto.statement.info.StatementStudent;
import com.project.database.dto.statement.shortInfo.StatementShortInfo;
import com.project.database.dto.statement.shortInfo.StudentsCount;
import com.project.database.dto.student.StudentShortInfo;
import com.project.database.entities.*;
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
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StatementServiceH {

    private final VidomistRepository vidomistRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final TutorRepository tutorRepository;


    public Optional<StatementInfo> getStatementInfo(int statementId) {
        return Optional.of(buildStatementInfo(statementId));
    }


    private StatementInfo buildStatementInfo(int statementId) {

        StatementHeaderEntity s1 = vidomistRepository.getStatementHeader(statementId)
                .stream().distinct().collect(Collectors.toList()).get(0);

        StatementFooterEntity s2 = vidomistRepository.getStatementFooter(statementId)
                .stream().distinct().collect(Collectors.toList()).get(0);

        List<StatementStudentEntity> s3 = vidomistRepository.getStatementStudent(statementId)
                .stream().distinct().collect(Collectors.toList());


        StatementHeader header = buildStatementHeader(s1);
        StatementFooter footer = buildStatementFooter(s2);
        List<StatementStudent> students = buildListStatementStudent(s3);

        StatementInfo statementInfo = new StatementInfo();
        statementInfo.setStatementHeader(header);
        statementInfo.setStatementFooter(footer);
        statementInfo.setStatementStudents(students);

        return statementInfo;
    }

    private StatementHeader buildStatementHeader(StatementHeaderEntity statementHeaderEntity) {
        StatementHeader statementHeader = new StatementHeader();
        statementHeader.setStatementNo(statementHeaderEntity.getStatementNo());
        statementHeader.setEduLevel(statementHeaderEntity.getEduLevel());
        statementHeader.setFaculty(statementHeaderEntity.getFaculty());
        statementHeader.setCourse(statementHeaderEntity.getCourse());
        statementHeader.setGroup(statementHeaderEntity.getGroupName());
        statementHeader.setSubjectName(statementHeaderEntity.getSubjectName());
        statementHeader.setSemester(statementHeaderEntity.getTrim());
        statementHeader.setCreditNumber("3");
        statementHeader.setControlType(statementHeaderEntity.getControlType());
        statementHeader.setExamDate(statementHeaderEntity.getExamDate());
        statementHeader.setTutorFullName(statementHeaderEntity.getTutorSurname() + " " + statementHeaderEntity.getTutorName() + " " + statementHeaderEntity.getTutorPatronymic());
        statementHeader.setTutorPosition(statementHeaderEntity.getTutorPosition());
        statementHeader.setTutorAcademicStatus(statementHeaderEntity.getTutorAcademicStatus());

        return statementHeader;
    }

    private StatementFooter buildStatementFooter(StatementFooterEntity sfe) {
        StatementFooter statementFooter = new StatementFooter();
        statementFooter.setPresentCount(sfe.getPresentCount());
        statementFooter.setAbsentCount(sfe.getAbsentCount());
        statementFooter.setRejectedCount(sfe.getRejectedCount());
        return statementFooter;
    }

    private List<StatementStudent> buildListStatementStudent(List<StatementStudentEntity> s3) {
        List<StatementStudent> statementStudentList = new ArrayList<>();
        for (int i = 0; i < s3.size(); i++) {
            StatementStudent statementStudent = new StatementStudent();
            StatementStudentEntity s = s3.get(i);
            statementStudent.setStudentId(s.getStudentId());
            statementStudent.setStudentPI(s.getStudentSurname()+" "+s.getStudentName());
            statementStudent.setStudentPatronymic(s.getStudentPatronymic());
            statementStudent.setStudentRecordBook(s.getStudentRecordBook());
            statementStudent.setSemesterGrade(s.getSemesterGrade());
            statementStudent.setControlGrade(s.getControlGrade());
            statementStudent.setTotalGrade(s.getTotalGrade());
            statementStudent.setNationalGrade(s.getNationalGrade());
            statementStudent.setEctsGrade(s.getEctsGrade());

            statementStudentList.add(statementStudent);
        }
        return statementStudentList;
    }


    VidomistEntity findByStatementNo(int statementNo) {
        return vidomistRepository.findByVidomistNo(statementNo);
    }


    public Page<StatementShortInfo> findAllStudentVidomosties(int studentCode, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        Page<Object[]> allStudentVidomosties = vidomistRepository.findAllStudentVidomosties(studentCode, pageable);
        return buildStatementShortInfo(allStudentVidomosties, pageable, allStudentVidomosties.getTotalPages());
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


    private Page<StatementShortInfo> buildStatementShortInfo(Page<Object[]> statementsP, Pageable pageable, int total) {
        List<StatementShortInfo> statementShortInfos = new ArrayList<>();
        List<Object[]> list = statementsP.getContent();
        for (int i = 0; i < statementsP.getNumberOfElements(); i++) {
            StatementShortInfo shortInfo = new StatementShortInfo();
            Object[] obj = list.get(i);
            int index = 0;
            shortInfo.setStatementNo((Integer) obj[index++]);
            shortInfo.setTutorFullName((String) obj[index++] + " " + obj[index++] + " " + obj[index++]);
            shortInfo.setSubjectName((String) obj[index++]);
            shortInfo.setGroup((String) obj[index++]);
            shortInfo.setControlType((String) obj[index++]);

            StudentsCount studentsCount = StudentsCount.builder()
                    .presentCount((Integer) obj[index++])
                    .absentCount((Integer) obj[index++])
                    .rejectedCount((Integer) obj[index++])
                    .build();

            shortInfo.setStudentsCount(studentsCount);
            shortInfo.setExamDate((LocalDate) obj[index++]);

            statementShortInfos.add(shortInfo);
        }
        return new PageImpl<>(statementShortInfos, pageable, total);

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
