package com.project.database.parser.parserBigunets.util.validators;

import com.project.database.dto.bigunets.errors.BigunetsHeaderErrors;
import com.project.database.dto.bigunets.errors.BigunetsStudentError;
import com.project.database.dto.bigunets.errors.BigunetsStudentErrorsMap;
import com.project.database.dto.bigunets.info.BigunetsHeader;
import com.project.database.dto.bigunets.info.BigunetsStudent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BigunValidator {
    public BigunetsHeaderErrors getHeaderErrors(BigunetsHeader bigunetsHeader) {

        BigunetsHeaderErrors bigunetsHeaderErrors = BigunetsHeaderErrors
                .builder()
                .controlTypeErrorText(new ArrayList<>())
                .courseErrorText(new ArrayList<>())
                .dueToErrorText(new ArrayList<>())
                .postponeReasonErrorText(new ArrayList<>())
                .creditNumberErrorText(new ArrayList<>())
                .eduLevelErrorText(new ArrayList<>())
                .examDateErrorText(new ArrayList<>())
                .facultyErrorText(new ArrayList<>())
                .groupErrorText(new ArrayList<>())
                .semesterErrorText(new ArrayList<>())
                .subjectNameErrorText(new ArrayList<>())
                .tutorAcademicStatusErrorText(new ArrayList<>())
                .tutorFullNameErrorText(new ArrayList<>())
                .tutorPositionErrorText(new ArrayList<>())
                .build();
        if (bigunetsHeader.getDueTo()==null)
        {
            bigunetsHeaderErrors.getDueToErrorText().add("Не вказано дату дійсний до");
        }else if (bigunetsHeader.getDueTo().compareTo(LocalDate.now()) < 0){
            bigunetsHeaderErrors.getDueToErrorText().add("Термін придатності (дійсний до) бігунця менший поточної дати");
        }
        if (bigunetsHeader.getPostponeReason().equals("") || bigunetsHeader.getPostponeReason()==null){
            bigunetsHeaderErrors.getPostponeReasonErrorText().add("Не вказано причину перенесення");
        }
        if (!bigunetsHeader.getEduLevel().equals("Магістр") && !bigunetsHeader.getEduLevel().equals("Бакалавр"))
            bigunetsHeaderErrors.getEduLevelErrorText().add("Невірно введений освітній рівень.");
        else if (bigunetsHeader.getEduLevel() == null || bigunetsHeader.getEduLevel().equals(""))
            bigunetsHeaderErrors.getEduLevelErrorText().add("Не введений освітній рівень");
        if (bigunetsHeader.getFaculty() == null || bigunetsHeader.getFaculty().equals(""))
            bigunetsHeaderErrors.getFacultyErrorText().add("Не введений факультет");
        if (!bigunetsHeader.getControlType().equals("залік") && !bigunetsHeader.getControlType().equals("екзамен"))
            bigunetsHeaderErrors.getControlTypeErrorText().add("Неправильно введенеий тип контролю");
        if (bigunetsHeader.getCourse() > 4 || (bigunetsHeader.getCourse() > 2 && bigunetsHeader.getEduLevel().equals("Магістр")) || bigunetsHeader.getCourse() == null)
            bigunetsHeaderErrors.getCourseErrorText().add("Неправильно введений курс.");
        if (bigunetsHeader.getCreditNumber() == null || bigunetsHeader.getCreditNumber().equals(""))
            bigunetsHeaderErrors.getCreditNumberErrorText().add("Не введені залікові бали.");
        if (bigunetsHeader.getExamDate() == null)
            bigunetsHeaderErrors.getExamDateErrorText().add("Неправильно введена дата");
        if (bigunetsHeader.getGroup() == null || bigunetsHeader.getGroup().equals("") )
            bigunetsHeaderErrors.getGroupErrorText().add("Не введена група");
        else if (!bigunetsHeader.getGroup().equals("бігунець"))
            bigunetsHeaderErrors.getGroupErrorText().add("Неправильно введена група(бігунець)");
        if (!bigunetsHeader.getSemester().equals("1") && !bigunetsHeader.getSemester().equals("2") && !bigunetsHeader.getSemester().equals("3") && !bigunetsHeader.getSemester().equals("4") && !bigunetsHeader.getSemester().equals("5") && !bigunetsHeader.getSemester().equals("6") && !bigunetsHeader.getSemester().equals("7") && !bigunetsHeader.getSemester().equals("8") && !bigunetsHeader.getSemester().equals("2д") && !bigunetsHeader.getSemester().equals("4д") && !bigunetsHeader.getSemester().equals("6д") && !bigunetsHeader.getSemester().equals("8д"))
            bigunetsHeaderErrors.getSemesterErrorText().add("Неправильно введений семестр");
        else if (bigunetsHeader.getSemester() == null || bigunetsHeader.getSemester().equals(""))
            bigunetsHeaderErrors.getSemesterErrorText().add("Не введено семестр.");
        if (bigunetsHeader.getSubjectName() == null || bigunetsHeader.getSubjectName().equals(""))
            bigunetsHeaderErrors.getSubjectNameErrorText().add("Не введено предмет.");
        if (bigunetsHeader.getTutorFullName() == null)
            bigunetsHeaderErrors.getTutorFullNameErrorText().add("Не вказано ПІБ викладача.");
        if (bigunetsHeader.getTutorAcademicStatus() == null)
            bigunetsHeaderErrors.getTutorAcademicStatusErrorText().add("Не вказано академічний статус викладача.");
        if (bigunetsHeader.getTutorPosition() == null)
            bigunetsHeaderErrors.getTutorPositionErrorText().add("Не вказана посада викладача.");
        return bigunetsHeaderErrors;
    }


    public BigunetsStudentErrorsMap getStudentErrors(List<BigunetsStudent> students) {
        BigunetsStudentErrorsMap bigunetsStudentErrorsMap = BigunetsStudentErrorsMap
                .builder()
                .bigunStudentErrorsMap(new HashMap<>())
                .build();

        for (BigunetsStudent student : students) {
            BigunetsStudentError bigunetsStudentError = BigunetsStudentError
                    .builder()
                    .controlGradeErrorText(new ArrayList<>())
                    .pibErrorText(new ArrayList<>())
                    .ectsGradeErrorText(new ArrayList<>())
                    .nationalGradeErrorText(new ArrayList<>())
                    .semesterGradeErrorText(new ArrayList<>())
                    .studentRecordBookErrorText(new ArrayList<>())
                    .totalGradeErrorText(new ArrayList<>())
                    .build();

            //todo clauses
            if (student.getTotalGrade() == null) {
                bigunetsStudentError.getTotalGradeErrorText().add("Не було введено значення загальної оцінки");
            } else if(student.getSemesterGrade()!=null && student.getControlGrade()!=null) {if(student.getTotalGrade() != student.getSemesterGrade() + student.getControlGrade()) {
                bigunetsStudentError.getTotalGradeErrorText().add("Загальна оцінка неправильно порахована");
            }}
            if (student.getStudentPI() == null || student.getStudentPI().equals(" ")) {
                bigunetsStudentError.getPibErrorText().add("Не вказано прізвище, ім'я");
            }
            if (student.getStudentPatronymic() == null || student.getStudentPatronymic().equals("")) {
                bigunetsStudentError.getPibErrorText().add("Не вказано по-батькові");
            }
            if (student.getStudentRecordBook() == null || student.getStudentRecordBook().equals("")) {
                bigunetsStudentError.getStudentRecordBookErrorText().add("Не вказано заліковку");
            }
            if (student.getSemesterGrade() == null) {
                bigunetsStudentError.getSemesterGradeErrorText().add("Не вказано оцінку за семестр");
            }
            if (student.getControlGrade() == null) {
                bigunetsStudentError.getControlGradeErrorText().add("Не вказано оцінку за екзамен/залік");
            }
            if (student.getTotalGrade() == null) {
                bigunetsStudentError.getTotalGradeErrorText().add("Не вказано загальну оцінку");
            } else if ((student.getTotalGrade() >= 91 && !student.getEctsGrade().equals("A"))
                    || (student.getTotalGrade() >= 81 && !student.getEctsGrade().equals("B") && student.getTotalGrade() <= 90)
                    || (student.getTotalGrade() >= 71 && !student.getEctsGrade().equals("C") && student.getTotalGrade() <= 80)
                    || (student.getTotalGrade() >= 66 && !student.getEctsGrade().equals("D") && student.getTotalGrade() <= 70)
                    || (student.getTotalGrade() >= 60 && !student.getEctsGrade().equals("E") && student.getTotalGrade() <= 65)
                    || (student.getTotalGrade() < 60 && !student.getEctsGrade().equals("F")))
            {
                bigunetsStudentError.getTotalGradeErrorText().add("Оцінка за ЄКТС не відповідає загальній оцінці");
            }
            if (student.getNationalGrade() == null) {
                bigunetsStudentError.getNationalGradeErrorText().add("Не вказано оцінку за нац. шкалою");
            } else if (!student.getNationalGrade().equals("Добре") && !student.getNationalGrade().equals("Задовільно") && !student.getNationalGrade().equals("Відмінно") && !student.getNationalGrade().equals("Зараховано") && !student.getNationalGrade().equals("Незараховано") && !student.getNationalGrade().equals("Недопущений") && !student.getNationalGrade().equals("Недопущена") && !student.getNationalGrade().equals("Недопущено")) {
                bigunetsStudentError.getNationalGradeErrorText().add("Неправильно вказано формат оцінки за нац. шкалою");
            } else if (student.getTotalGrade() != null) {
                if ((student.getTotalGrade() >= 91 && (!student.getNationalGrade().equals("Відмінно") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() >= 81 && student.getTotalGrade() <= 90 && (!student.getNationalGrade().equals("Добре") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() >= 71 && student.getTotalGrade() <= 80 && (!student.getNationalGrade().equals("Добре") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() >= 66 && student.getTotalGrade() <= 70 && (!student.getNationalGrade().equals("Задовільно") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() >= 60 && student.getTotalGrade() <= 65 && (!student.getNationalGrade().equals("Задовільно") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() < 60 && (!student.getNationalGrade().equals("Незараховано") && !student.getNationalGrade().equals("Не допущений")))) {
                    bigunetsStudentError.getNationalGradeErrorText().add("Оцінка за національною шкалою не відповідає загальній оцінці");
                }
            }
            if (student.getEctsGrade() == null) {
                bigunetsStudentError.getEctsGradeErrorText().add("Не вказано оцінку у системі ЄКТС");
            } else if (!student.getEctsGrade().equals("A") && !student.getEctsGrade().equals("B") && !student.getEctsGrade().equals("C") && !student.getEctsGrade().equals("D") && !student.getEctsGrade().equals("E") ) {
                bigunetsStudentError.getEctsGradeErrorText().add("Не вірно вказан формат оцінки у ЄКТС");
            } else if (student.getEctsGrade().equals("F"))
            {
                bigunetsStudentError.getEctsGradeErrorText().add("У бігунці не може бути оцінка за ЄКТС: F");
            }
//            if (student.getSemesterGrade() != null && student.getControlGrade() != null && student.getTotalGrade() != null) {
//                if (student.getSemesterGrade() + student.getControlGrade() != student.getTotalGrade()) {
//                    statementStudentError.getTotalGradeErrorText().add("Неправильно порахована сума оцінок за семестр і за екзамен/залік");
//                }
//                //if (student.getTotalGrade() > 60 && student.getNationalGrade())
//            }

            bigunetsStudentErrorsMap.getBigunStudentErrorsMap().put(student.getStudentRecordBook(), bigunetsStudentError);
        }
        return bigunetsStudentErrorsMap;
    }
}
