package com.project.database.serviceHibernate;

import com.project.database.dto.bigunets.info.BigunetsHeader;
import com.project.database.dto.bigunets.info.BigunetsInfo;
import com.project.database.dto.bigunets.info.BigunetsStudent;
import com.project.database.dto.statement.info.StatementFooter;
import com.project.database.dto.statement.info.StatementHeader;
import com.project.database.dto.statement.info.StatementInfo;
import com.project.database.dto.statement.info.StatementStudent;
import com.project.database.entities.*;
import com.project.database.exception.StatementNotExist;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    private final VidomistMarkServiceH vidomistMarkServiceH;


    // not tested -> todo test
    @Transactional
    public void insertBigunets(BigunetsInfo bigunetsInfo) throws StatementNotExist {
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

        // subject
        SubjectEntity subject = new SubjectEntity();
        subject.setSubjectName(header.getSubjectName());
        List<GroupEntity> gropsList = groupServiceH.findAllBySubjectName(subject.getSubjectName());

        subject.setEduLevel(header.getEduLevel());
        subject.setFaculty(header.getFaculty());
        subject.setGroup(gropsList);
        subject.setCredits(header.getCreditNumber());

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
        group.setSubject(subjectServiceH.findBySubjectName(header.getSubjectName()));

        // bigunets
        BigunetsEntity bigunets = new BigunetsEntity();
        bigunets.setBigunetsNo(header.getBigunNo());
        bigunets.setExamDate(header.getExamDate());
        bigunets.setValidUntil(header.getDueTo());
        bigunets.setPostpReason(header.getPostponeReason());
        bigunets.setControlType(header.getControlType());
        TutorEntity tutorEntity = tutorServiceH.findByPIB(tutor.getTutorSurname(), tutor.getTutorName(), tutor.getTutorPatronymic());
        bigunets.setTutor(tutorEntity);

        ArrayList<StudentEntity> studentEntities = new ArrayList<>(students.size());
        List<BigunetsMarkEntity> markEntities = new ArrayList<>(students.size());

        // bigunets_mark
        for (BigunetsStudent bs : students) {
            // student
            StudentEntity student = new StudentEntity();
            String[] studentPI = bs.getStudentPI().split(" ");
            student.setStudentSurname(studentPI[0]);
            student.setStudentName(studentPI[1]);
            student.setStudentPatronymic(bs.getStudentPatronymic());
            student.setStudentRecordBook(bs.getStudentRecordBook());
            studentEntities.add(student);

            // vidomistNO
            VidomistEntity vidomistEntity = vidomistServiceH.findVidomistNoByStudentRecordBookAndSubjectName(bs.getStudentRecordBook(), subject.getSubjectName());
            StudentEntity studentEntity = studentServiceH.findByStudentRecordBook(bs.getStudentRecordBook());

            // додав цю перевірку на випадок, коли відомості (за НОМЕРОМ студента та предметм ще немає)
            if (vidomistEntity != null) {
                // bigunetsMark
                BigunetsMarkEntity bigunetsMark = new BigunetsMarkEntity();
                // bigunetsMarkId
                BigunetsMarkId bigunetsMarkId = new BigunetsMarkId();
                bigunetsMarkId.setBigunetsNo(header.getBigunNo());
                bigunetsMarkId.setStudentCode(studentEntity.getStudentCode());
                bigunetsMarkId.setVidomistNo(vidomistEntity.getVidomistNo());
                bigunetsMarkId.setTutorNo(tutorEntity.getTutorNo());

                bigunetsMark.setBigunetsMarkId(bigunetsMarkId);
                bigunetsMark.setTrimMark(bs.getSemesterGrade());
                bigunetsMark.setMarkCheck(bs.getControlGrade());
                bigunetsMark.setCompleteMark(bs.getTotalGrade());
                bigunetsMark.setNatMark(bs.getNationalGrade());
                bigunetsMark.setEctsMark(bs.getEctsGrade());

                markEntities.add(bigunetsMark);
            } else
                throw new StatementNotExist("Statement not exists");
        }

        tutorServiceH.insertTutor(tutor);
        subjectServiceH.insertSubject(subject);
        groupServiceH.insertGroup(group, subject);
        bigunetsServiceH.insertBigunets(bigunets);
        for (StudentEntity studentEntity : studentEntities) {
            studentServiceH.insertStudent(studentEntity);
        }
        for (BigunetsMarkEntity markEntity : markEntities) {
            bigunetsMarkServiceH.insertBigunetsMark(markEntity);
        }

    }


    // todo check how works
    public void insertVidomist(StatementInfo statementInfo) {

        StatementHeader header = statementInfo.getStatementHeader();
        StatementFooter footer = statementInfo.getStatementFooter();
        List<StatementStudent> students = statementInfo.getStatementStudents();

        // + tutor
        // + subject
        // + group
        //  + vidomist
        // student
        // vidomist_mark

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
        List<GroupEntity> gropsList = groupServiceH.findAllBySubjectName(subject.getSubjectName());

        subject.setEduLevel(header.getEduLevel());
        subject.setFaculty(header.getFaculty());
        subject.setGroup(gropsList);
        subject.setCredits(header.getCreditNumber());
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
        group.setSubject(subjectServiceH.findBySubjectName(header.getSubjectName()));
        groupServiceH.insertGroup(group, subject);

        // vidomist
        VidomistEntity vidomist = new VidomistEntity();
        //tutor
        TutorEntity tutorEntity = tutorServiceH.findByPIB(tutor.getTutorSurname(), tutor.getTutorName(), tutor.getTutorPatronymic());

        vidomist.setVidomistNo(header.getStatementNo());
        vidomist.setTutor(tutorEntity);
        vidomist.setPresentCount(footer.getPresentCount());
        vidomist.setAbsentCount(footer.getAbsentCount());
        vidomist.setRejectedCount(footer.getRejectedCount());
        vidomist.setControlType(header.getControlType());
        vidomist.setExamDate(header.getExamDate());
        GroupEntity groupEntity = groupServiceH.findGroupByNameYearTrimCourseSubject(group, subject);
        vidomist.setGroup(groupEntity);
        vidomistServiceH.insertVidomist(vidomist);


        for (int i = 0; i < students.size(); i++) {
            StatementStudent ss = students.get(i);
            // student
            StudentEntity student = new StudentEntity();
            String[] studentPI = ss.getStudentPI().split(" ");
            student.setStudentSurname(studentPI[0]);
            student.setStudentName(studentPI[1]);
            student.setStudentPatronymic(ss.getStudentPatronymic());
            student.setStudentRecordBook(ss.getStudentRecordBook());
            studentServiceH.insertStudent(student);

            // vidomistMark
            VidomistMarkEntity vidomistMark = new VidomistMarkEntity();
            VidomistMarkId vidomistMarkId = new VidomistMarkId();
            StudentEntity studentEntity = studentServiceH.findByStudentRecordBook(student.getStudentRecordBook());
            vidomistMarkId.setStudentCode(studentEntity.getStudentCode());
            //            vidomistMarkId.setVidomistNo(header.getStatementNo());
            vidomistMarkId.setVidomistNo(header.getStatementNo());

            vidomistMark.setVidomistMarkId(vidomistMarkId);
            vidomistMark.setTrimMark(ss.getSemesterGrade());
            vidomistMark.setNatMark(ss.getNationalGrade());
            vidomistMark.setMarkCheck(ss.getControlGrade());
            vidomistMark.setCompleteMark(ss.getTotalGrade());
            vidomistMark.setEctsMark(ss.getEctsGrade());
            // todo may check
            vidomistMarkServiceH.insertVidomistMark(vidomistMark);
        }
    }

}
