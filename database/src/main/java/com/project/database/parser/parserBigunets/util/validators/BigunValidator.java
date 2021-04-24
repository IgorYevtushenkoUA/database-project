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
    public BigunetsHeaderErrors getHeaderErrors(BigunetsHeader bigunHeader) {

        BigunetsHeaderErrors bigunHeaderErrors = BigunetsHeaderErrors
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
        if (bigunHeader.getDueTo() == null) {
            bigunHeaderErrors.getDueToErrorText().add("Не вказано дату дійсний до");
        } else if (bigunHeader.getDueTo().compareTo(LocalDate.now()) < 0) {
            bigunHeaderErrors.getDueToErrorText().add("Термін придатності (дійсний до) бігунця менший поточної дати");
        }
        if (bigunHeader.getPostponeReason() == null || bigunHeader.getPostponeReason().equals("")) {
            bigunHeaderErrors.getPostponeReasonErrorText().add("Не вказано причину перенесення");
        }
        if (!bigunHeader.getEduLevel().equals("Магістр") && !bigunHeader.getEduLevel().equals("Бакалавр"))
            bigunHeaderErrors.getEduLevelErrorText().add("Невірно введений освітній рівень.");
        else if (bigunHeader.getEduLevel() == null || bigunHeader.getEduLevel().equals(""))
            bigunHeaderErrors.getEduLevelErrorText().add("Не введений освітній рівень");
        if (bigunHeader.getFaculty() == null || bigunHeader.getFaculty().equals(""))
            bigunHeaderErrors.getFacultyErrorText().add("Не введений факультет");
        if (!bigunHeader.getControlType().equals("залік") && !bigunHeader.getControlType().equals("екзамен"))
            bigunHeaderErrors.getControlTypeErrorText().add("Неправильно введенеий тип контролю");
        if (bigunHeader.getCourse() > 4 || (bigunHeader.getCourse() > 2 && bigunHeader.getEduLevel().equals("Магістр")) || bigunHeader.getCourse() == null)
            bigunHeaderErrors.getCourseErrorText().add("Неправильно введений курс.");
        if (bigunHeader.getCreditNumber() == null || bigunHeader.getCreditNumber().equals(""))
            bigunHeaderErrors.getCreditNumberErrorText().add("Не введені залікові бали.");
        if (bigunHeader.getExamDate() == null)
            bigunHeaderErrors.getExamDateErrorText().add("Неправильно введена дата");
        if (bigunHeader.getGroup() == null || bigunHeader.getGroup().equals(""))
            bigunHeaderErrors.getGroupErrorText().add("Не введена група");
        else if (!bigunHeader.getGroup().equals("бігунець"))
            bigunHeaderErrors.getGroupErrorText().add("Неправильно введена група(бігунець)");
        if (!bigunHeader.getSemester().equals("1") && !bigunHeader.getSemester().equals("2") && !bigunHeader.getSemester().equals("3") && !bigunHeader.getSemester().equals("4") && !bigunHeader.getSemester().equals("5") && !bigunHeader.getSemester().equals("6") && !bigunHeader.getSemester().equals("7") && !bigunHeader.getSemester().equals("8") && !bigunHeader.getSemester().equals("2д") && !bigunHeader.getSemester().equals("4д") && !bigunHeader.getSemester().equals("6д") && !bigunHeader.getSemester().equals("8д"))
            bigunHeaderErrors.getSemesterErrorText().add("Неправильно введений семестр");
        else if (bigunHeader.getSemester() == null || bigunHeader.getSemester().equals(""))
            bigunHeaderErrors.getSemesterErrorText().add("Не введено семестр.");
        if (bigunHeader.getSubjectName() == null || bigunHeader.getSubjectName().equals(""))
            bigunHeaderErrors.getSubjectNameErrorText().add("Не введено предмет.");
        if (bigunHeader.getTutorFullName() == null)
            bigunHeaderErrors.getTutorFullNameErrorText().add("Не вказано ПІБ викладача.");
        if (bigunHeader.getTutorAcademicStatus() == null)
            bigunHeaderErrors.getTutorAcademicStatusErrorText().add("Не вказано академічний статус викладача.");
        if (bigunHeader.getTutorPosition() == null)
            bigunHeaderErrors.getTutorPositionErrorText().add("Не вказана посада викладача.");
        return bigunHeaderErrors;
    }


    public BigunetsStudentErrorsMap getStudentErrors(List<BigunetsStudent> students) {
        BigunetsStudentErrorsMap bigunStudentErrorsMap = BigunetsStudentErrorsMap
                .builder()
                .bigunStudentErrorsMap(new HashMap<>())
                .build();

        for (BigunetsStudent student : students) {
            BigunetsStudentError bigunStudentError = BigunetsStudentError
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
            if (student.getNationalGrade() != null) {
                if (student.getTotalGrade() == null && (!student.getNationalGrade().equals("Недопущений") || !student.getNationalGrade().equals("Недопущена") || !student.getNationalGrade().equals("Недопущено") || !student.getNationalGrade().equals("Невідвідував") || !student.getNationalGrade().equals("Невідвідувала"))) {
                    bigunStudentError.getTotalGradeErrorText().add("Не було введено значення загальної оцінки");
                } else if (student.getSemesterGrade() != null && student.getControlGrade() != null) {
                    if (student.getTotalGrade() != student.getSemesterGrade() + student.getControlGrade()) {
                        bigunStudentError.getTotalGradeErrorText().add("Загальна оцінка неправильно порахована");
                    }
                }
            } else if (student.getTotalGrade() == null) {
                bigunStudentError.getTotalGradeErrorText().add("Не було введено значення загальної оцінки");
            }
            if (student.getStudentPI() == null || student.getStudentPI().equals(" ")) {
                bigunStudentError.getPibErrorText().add("Не вказано прізвище, ім'я");
            }
            if (student.getStudentPatronymic() == null || student.getStudentPatronymic().equals("")) {
                bigunStudentError.getPibErrorText().add("Не вказано по-батькові");
            }
            if (student.getStudentRecordBook() == null || student.getStudentRecordBook().equals("")) {
                bigunStudentError.getStudentRecordBookErrorText().add("Не вказано заліковку");
            }
            if (student.getNationalGrade() != null) {
                if (student.getSemesterGrade() == null && (!student.getNationalGrade().equals("Недопущений") && !student.getNationalGrade().equals("Недопущена") && !student.getNationalGrade().equals("Недопущено") && !student.getNationalGrade().equals("Невідвідував") && !student.getNationalGrade().equals("Невідвідувала"))) {
                    bigunStudentError.getSemesterGradeErrorText().add("Не вказано оцінку за семестр");
                }
            } else if (student.getSemesterGrade() == null) {
                bigunStudentError.getSemesterGradeErrorText().add("Не вказано оцінку за семестр");
            }
            if (student.getNationalGrade() != null) {
                if (student.getControlGrade() == null && (!student.getNationalGrade().equals("Недопущений") && !student.getNationalGrade().equals("Недопущена") && !student.getNationalGrade().equals("Недопущено") && !student.getNationalGrade().equals("Невідвідував") && !student.getNationalGrade().equals("Невідвідувала"))) {
                    bigunStudentError.getControlGradeErrorText().add("Не вказано оцінку за екзамен/залік");
                }
            } else if (student.getControlGrade() == null) {
                bigunStudentError.getControlGradeErrorText().add("Не вказано оцінку за екзамен/залік");
            }


            if (student.getNationalGrade() != null) {
                if (student.getTotalGrade() == null && (!student.getNationalGrade().equals("Недопущений") && !student.getNationalGrade().equals("Недопущена") && !student.getNationalGrade().equals("Недопущено") && !student.getNationalGrade().equals("Невідвідував") && !student.getNationalGrade().equals("Невідвідувала"))) {
                    bigunStudentError.getTotalGradeErrorText().add("Не вказано загальну оцінку");
                } else if (student.getTotalGrade() != null && student.getEctsGrade()!=null && ((student.getTotalGrade() >= 91 && !student.getEctsGrade().equals("A"))
                        || (student.getTotalGrade() >= 81 && !student.getEctsGrade().equals("B") && student.getTotalGrade() <= 90)
                        || (student.getTotalGrade() >= 71 && !student.getEctsGrade().equals("C") && student.getTotalGrade() <= 80)
                        || (student.getTotalGrade() >= 66 && !student.getEctsGrade().equals("D") && student.getTotalGrade() <= 70)
                        || (student.getTotalGrade() >= 60 && !student.getEctsGrade().equals("E") && student.getTotalGrade() <= 65)
                        || (student.getTotalGrade() < 60 && !student.getEctsGrade().equals("F")))) {
                    bigunStudentError.getEctsGradeErrorText().add("Оцінка за ЄКТС не відповідає загальній оцінці");
                }
            } else if (student.getTotalGrade() == null) {
                bigunStudentError.getTotalGradeErrorText().add("Не вказано загальну оцінку");
            }

            if (student.getNationalGrade() == null) {
                bigunStudentError.getNationalGradeErrorText().add("Не вказано оцінку за нац. шкалою");
            } else if (!student.getNationalGrade().equals("Добре") && !student.getNationalGrade().equals("Задовільно") && !student.getNationalGrade().equals("Незадовільно") && !student.getNationalGrade().equals("Відмінно") && !student.getNationalGrade().equals("Зараховано") && !student.getNationalGrade().equals("Незараховано") && !student.getNationalGrade().equals("Недопущений") && !student.getNationalGrade().equals("Недопущена") && !student.getNationalGrade().equals("Недопущено") && !student.getNationalGrade().equals("Незараховано") && !student.getNationalGrade().equals("Незарахована") && !student.getNationalGrade().equals("Незарахований") && !student.getNationalGrade().equals("Невідвідував") && !student.getNationalGrade().equals("Невідвідувала")) {
                bigunStudentError.getNationalGradeErrorText().add("Неправильно вказано формат оцінки за нац. шкалою");
            } else if (student.getTotalGrade() != null) {
                if ((student.getTotalGrade() >= 91 && (!student.getNationalGrade().equals("Відмінно") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() >= 81 && student.getTotalGrade() <= 90 && (!student.getNationalGrade().equals("Добре") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() >= 71 && student.getTotalGrade() <= 80 && (!student.getNationalGrade().equals("Добре") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() >= 66 && student.getTotalGrade() <= 70 && (!student.getNationalGrade().equals("Задовільно") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() >= 60 && student.getTotalGrade() <= 65 && (!student.getNationalGrade().equals("Задовільно") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() < 60 && (!student.getNationalGrade().equals("Незараховано") && !student.getNationalGrade().equals("Незадовільно") && !student.getNationalGrade().equals("Недопущений") && !student.getNationalGrade().equals("Недопущена") && !student.getNationalGrade().equals("Недопущено") && !student.getNationalGrade().equals("Незараховано") && !student.getNationalGrade().equals("Незарахована") && !student.getNationalGrade().equals("Незарахований") && !student.getNationalGrade().equals("Невідвідував") && !student.getNationalGrade().equals("Невідвідувала")))) {
                    bigunStudentError.getNationalGradeErrorText().add("Оцінка за національною шкалою не відповідає загальній оцінці");
                }
            }


            if (student.getEctsGrade() == null) {
                bigunStudentError.getEctsGradeErrorText().add("Не вказано оцінку у системі ЄКТС");
            } else if (!student.getEctsGrade().equals("A") && !student.getEctsGrade().equals("B") && !student.getEctsGrade().equals("C") && !student.getEctsGrade().equals("D") && !student.getEctsGrade().equals("E")) {
                bigunStudentError.getEctsGradeErrorText().add("Не вірно вказан формат оцінки у ЄКТС");
            } else if (student.getEctsGrade().equals("F")) {
                bigunStudentError.getEctsGradeErrorText().add("У бігунці не може бути оцінка за ЄКТС: F");
            }
//            if (student.getSemesterGrade() != null && student.getControlGrade() != null && student.getTotalGrade() != null) {
//                if (student.getSemesterGrade() + student.getControlGrade() != student.getTotalGrade()) {
//                    statementStudentError.getTotalGradeErrorText().add("Неправильно порахована сума оцінок за семестр і за екзамен/залік");
//                }
//                //if (student.getTotalGrade() > 60 && student.getNationalGrade())
//            }

            bigunStudentErrorsMap.getBigunStudentErrorsMap().put(student.getStudentId(), bigunStudentError);
        }
        return bigunStudentErrorsMap;
    }
}
