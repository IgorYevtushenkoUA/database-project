package com.project.database.serviceHibernate;

import com.project.database.dto.bigunets.info.BigunetsHeader;
import com.project.database.dto.bigunets.info.BigunetsInfo;
import com.project.database.dto.bigunets.info.BigunetsStudent;
import com.project.database.dto.statement.info.StatementInfo;
import com.project.database.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ParserServiceH {

    @Autowired
    private final TutorServiceH tutorServiceH;
    @Autowired
    private final SubjectServiceH subjectServiceH;
    @Autowired
    private final GroupServiceH groupServiceH;
    @Autowired
    private final BigunetsServiceH bigunetsServiceH;
    @Autowired
    private final StudentServiceH studentServiceH;
    @Autowired
    private final VidomistServiceH vidomistServiceH;
    @Autowired
    private final BigunetsMarkServiceH bigunetsMarkServiceH;

    // not tested -> todo test
    public void insertBigunets(BigunetsInfo bigunetsInfo) {
        // + tutor
        // + subject
        // + group
        // + bigunets
        // - vidomistNO
        // student
        // bigunets_mark
        BigunetsHeader header = bigunetsInfo.getBigunetsHeader();
        List<BigunetsStudent> students = bigunetsInfo.getBigunetsStudents();


        // tutor
        TutorEntity tutor = new TutorEntity();
        String[] tutorFullName = header.getTutorFullName().split(" ");
        tutor.setTutorSurname(tutorFullName[0]);
        tutor.setTutorName(tutorFullName[1]);
        tutor.setTutorPatronymic(tutorFullName[2]);
        tutor.setScienceDegree(""); // todo бігунець це не повертає
        tutor.setAcademStatus(header.getTutorAcademicStatus());
        tutor.setPosition(header.getTutorPosition());
        tutorServiceH.insertTutor(tutor);
        // subject
        SubjectEntity subject = new SubjectEntity();
        subject.setSubjectName(header.getSubjectName());
        subject.setEduLevel(header.getEduLevel());
        subject.setFaculty(header.getFaculty());
        subjectServiceH.insertSubject(subject);
        // group
        GroupEntity group = new GroupEntity();
        int groupYear = header.getExamDate().getYear();
        int month = header.getExamDate().getMonthValue();
        String eduYear = month > 9
                ? groupYear + "-" + (groupYear + 1)
                : (groupYear - 1) + "-" + groupYear;

        group.setGroupName(header.getGroup());
        group.setEduYear(eduYear);
        group.setTrim(header.getSemester());
        group.setCourse(header.getCourse());
        groupServiceH.insertGroup(group, subject);
        // bigunets
        BigunetsEntity bigunets = new BigunetsEntity();
        bigunets.setBigunetsNo(header.getBigunNo());
        bigunets.setExamDate(header.getExamDate());
        bigunets.setValidUntil(header.getDueTo());
        bigunets.setPostpReason(header.getPostponeReason());
        bigunets.setControlType(header.getControlType());
        TutorEntity tutorEntity = tutorServiceH.findByPIB(tutor.getTutorSurname(), tutor.getTutorName(), tutor.getTutorPatronymic());
        bigunets.setTutor(tutorEntity);
        bigunetsServiceH.insertBigunets(bigunets);


        // bigunets_mark
        for (int i = 0; i < students.size(); i++) {
            // student
            BigunetsStudent bs = students.get(i);
            StudentEntity student = new StudentEntity();
            String[] studentPI = bs.getStudentPI().split(" ");
            student.setStudentSurname(studentPI[0]);
            student.setStudentName(studentPI[1]);
            student.setStudentPatronymic(bs.getStudentPatronymic());
            student.setStudentRecordBook(bs.getStudentRecordBook());
            studentServiceH.insertStudent(student);

            // vidomistNO
            VidomistEntity vidomistEntity = vidomistServiceH.findVidomistNoByStudentRecordBookAndSubjectName(bs.getStudentRecordBook(), subject.getSubjectName());
            StudentEntity studentEntity = studentServiceH.findByStudentRecordBook(bs.getStudentRecordBook());

            // bigunetsMark
            BigunetsMarkEntity bigunetsMark = new BigunetsMarkEntity();
            // bigunetsMarkId
            BigunetsMarkId bigunetsMarkId = new BigunetsMarkId();
            bigunetsMarkId.setBigunetsNo(header.getBigunNo());
            bigunetsMarkId.setStudentCode(studentEntity.getStudentCode());
            bigunetsMarkId.setVidomistNo(vidomistEntity.getVidomistNo());
            bigunetsMarkId.setTutorNo(tutorEntity.getTutorNo());

            bigunetsMark.setTrimMark(bs.getSemesterGrade());
            bigunetsMark.setMarkCheck(bs.getControlGrade());
            bigunetsMark.setCompleteMark(bs.getTotalGrade());
            bigunetsMark.setNatMark(bs.getNationalGrade());
            bigunetsMark.setEctsMark(bs.getEctsGrade());

            bigunetsMarkServiceH.insertBigunetsMark(bigunetsMark);
        }

    }

    public void insertVidomist(StatementInfo statementInfo) {

    }

}
