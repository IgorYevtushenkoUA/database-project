package com.project.database.parser.parserStatement.util.validators;

import com.project.database.dto.statement.errors.StatementFooterErrors;
import com.project.database.dto.statement.errors.StatementHeaderErrors;
import com.project.database.dto.statement.errors.StatementStudentError;
import com.project.database.dto.statement.errors.StatementStudentErrorsMap;
import com.project.database.dto.statement.info.StatementFooter;
import com.project.database.dto.statement.info.StatementHeader;
import com.project.database.dto.statement.info.StatementStudent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatementValidator {
    public StatementHeaderErrors getHeaderErrors(StatementHeader statementHeader) {

        StatementHeaderErrors statementHeaderErrors = StatementHeaderErrors
                .builder()
                .controlTypeErrorText(new ArrayList<>())
                .courseErrorText(new ArrayList<>())
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

        if (!statementHeader.getEduLevel().equals("Магістр") && !statementHeader.getEduLevel().equals("Бакалавр"))
            statementHeaderErrors.getEduLevelErrorText().add("Невірно введений освітній рівень.");
        else if (statementHeader.getEduLevel() == null || statementHeader.getEduLevel().equals(""))
            statementHeaderErrors.getEduLevelErrorText().add("Не введений освітній рівень");
        if (statementHeader.getFaculty() == null || statementHeader.getFaculty().equals(""))
            statementHeaderErrors.getFacultyErrorText().add("Не введений факультет");
        if (!statementHeader.getControlType().equals("залік") && !statementHeader.getControlType().equals("екзамен"))
            statementHeaderErrors.getControlTypeErrorText().add("Неправильно введенеий тип контролю");
        if (statementHeader.getCourse() > 4 || (statementHeader.getCourse() > 2 && statementHeader.getEduLevel().equals("Магістр")) || statementHeader.getCourse() == null)
            statementHeaderErrors.getCourseErrorText().add("Неправильно введений курс.");
        if (statementHeader.getCreditNumber() == null || statementHeader.getCreditNumber().equals(""))
            statementHeaderErrors.getCreditNumberErrorText().add("Не введені залікові бали.");
        if (statementHeader.getExamDate() == null)
            statementHeaderErrors.getExamDateErrorText().add("Неправильно введена дата");
        if (statementHeader.getGroup() == null || statementHeader.getGroup().equals(""))
            statementHeaderErrors.getGroupErrorText().add("Не введена група");
        else if (statementHeader.getGroup().length() > 3 && !statementHeader.getGroup().matches("[1-9]\\d?І"))
            statementHeaderErrors.getGroupErrorText().add("Неправильно введена група");
        if (!statementHeader.getSemester().equals("1") && !statementHeader.getSemester().equals("2") && !statementHeader.getSemester().equals("3") && !statementHeader.getSemester().equals("4") && !statementHeader.getSemester().equals("5") && !statementHeader.getSemester().equals("6") && !statementHeader.getSemester().equals("7") && !statementHeader.getSemester().equals("8") && !statementHeader.getSemester().equals("2д") && !statementHeader.getSemester().equals("4д") && !statementHeader.getSemester().equals("6д") && !statementHeader.getSemester().equals("8д"))
            statementHeaderErrors.getSemesterErrorText().add("Неправильно введений семестр");
        else if (statementHeader.getSemester() == null || statementHeader.getSemester().equals(""))
            statementHeaderErrors.getSemesterErrorText().add("Не введено семестр.");
        if (statementHeader.getSubjectName() == null || statementHeader.getSubjectName().equals(""))
            statementHeaderErrors.getSubjectNameErrorText().add("Не введено предмет.");
        if (statementHeader.getTutorFullName() == null)
            statementHeaderErrors.getTutorFullNameErrorText().add("Не вказано ПІБ викладача.");
        if (statementHeader.getTutorAcademicStatus() == null)
            statementHeaderErrors.getTutorAcademicStatusErrorText().add("Не вказано академічний статус викладача.");
        if (statementHeader.getTutorPosition() == null)
            statementHeaderErrors.getTutorPositionErrorText().add("Не вказана посада викладача.");
        return statementHeaderErrors;
    }

    public StatementFooterErrors getFooterErrors(StatementFooter statementFooter, List<StatementStudent> students) {
        // BigunetsInfo statementInfo = new BigunetsInfo();
        StatementFooterErrors statementFooterErrors = StatementFooterErrors
                .builder()
                .absentCountErrorText(new ArrayList<>())
                .rejectedCountErrorText(new ArrayList<>())
                .presentCountErrorText(new ArrayList<>())
                .build();
        //todo clauses
        if (statementFooter.getAbsentCount() == null) {
            statementFooterErrors.getAbsentCountErrorText().add("Не було введено значення кількості відсутніх студентів");
        }
        if (statementFooter.getRejectedCount() == null) {
            statementFooterErrors.getRejectedCountErrorText().add("Не було введено значення кількості недопущенних студентів");
        }
        if (statementFooter.getPresentCount() == null) {
            statementFooterErrors.getPresentCountErrorText().add("Не було введено значення кількості присутніх студентів");
        }
        if (statementFooter.getAbsentCount() != null && statementFooter.getRejectedCount() != null && statementFooter.getPresentCount() != null)
        {
            if (statementFooter.getPresentCount() + statementFooter.getRejectedCount() + statementFooter.getAbsentCount() != students.size()) {
              statementFooterErrors.getPresentCountErrorText().add("Сумма присутніх, недопущених, відсутніх не збігаєтсья з кількістю студентів групи.");
            }
        }
//        if (statementFooter.getAbsentCount() != null && statementFooter.getRejectedCount() != null && statementFooter.getPresentCount() != null) {
//            System.out.println(statementInfo.getStatementStudents().size());
//            if (statementFooter.getPresentCount() + statementFooter.getRejectedCount() + statementFooter.getAbsentCount() != statementInfo.getStatementStudents().size()) {
//                statementFooterErrors.getPresentCountErrorText().add("Сумма присутніх, недопущених, відсутніх не збігаєтсья з кількістю студентів групи.");
//            }
//        } Немає доступу


        return statementFooterErrors;
    }

    public StatementStudentErrorsMap getStudentErrors(List<StatementStudent> students) {
        StatementStudentErrorsMap statementStudentErrorsMap = StatementStudentErrorsMap
                .builder()
                .statementStudentsErrorsMap(new HashMap<>())
                .build();

        for (StatementStudent student : students) {
            StatementStudentError statementStudentError = StatementStudentError
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
                if (student.getTotalGrade() == null && (!student.getNationalGrade().equals("Недопущений") && !student.getNationalGrade().equals("Недопущена") && !student.getNationalGrade().equals("Недопущено") && !student.getNationalGrade().equals("Невідвідував") && !student.getNationalGrade().equals("Невідвідувала"))) {
                    statementStudentError.getTotalGradeErrorText().add("Не було введено значення загальної оцінки");
                } else if (student.getSemesterGrade() != null && student.getControlGrade() != null) {
                    if (student.getTotalGrade() != student.getSemesterGrade() + student.getControlGrade()) {
                        statementStudentError.getTotalGradeErrorText().add("Загальна оцінка неправильно порахована");
                    }
                }
            } else if (student.getTotalGrade() == null) {
                statementStudentError.getTotalGradeErrorText().add("Не було введено значення загальної оцінки");
            }
            if (student.getStudentPI() == null || student.getStudentPI().equals(" ")) {
                statementStudentError.getPibErrorText().add("Не вказано прізвище, ім'я");
            }
            if (student.getStudentPatronymic() == null || student.getStudentPatronymic().equals("")) {
                statementStudentError.getPibErrorText().add("Не вказано по-батькові");
            }
            if (student.getStudentRecordBook() == null || student.getStudentRecordBook().equals("")) {
                statementStudentError.getStudentRecordBookErrorText().add("Не вказано заліковку");
            }
            if (student.getNationalGrade() != null) {
                if (student.getSemesterGrade() == null && (!student.getNationalGrade().equals("Недопущений") && !student.getNationalGrade().equals("Недопущена") && !student.getNationalGrade().equals("Недопущено") && !student.getNationalGrade().equals("Невідвідував") && !student.getNationalGrade().equals("Невідвідувала"))) {
                    statementStudentError.getSemesterGradeErrorText().add("Не вказано оцінку за семестр");
                }
            } else if (student.getSemesterGrade() == null) {
                statementStudentError.getSemesterGradeErrorText().add("Не вказано оцінку за семестр");
            }
            if (student.getNationalGrade() != null) {
                if (student.getControlGrade() == null && (!student.getNationalGrade().equals("Недопущений") && !student.getNationalGrade().equals("Недопущена") && !student.getNationalGrade().equals("Недопущено") && !student.getNationalGrade().equals("Невідвідував") && !student.getNationalGrade().equals("Невідвідувала"))) {
                    statementStudentError.getControlGradeErrorText().add("Не вказано оцінку за екзамен/залік");
                }
            } else if (student.getControlGrade() == null) {
                statementStudentError.getControlGradeErrorText().add("Не вказано оцінку за екзамен/залік");
            }
            if (student.getNationalGrade() != null) {
                if (student.getTotalGrade() == null && (!student.getNationalGrade().equals("Недопущений") && !student.getNationalGrade().equals("Недопущена") && !student.getNationalGrade().equals("Недопущено") && !student.getNationalGrade().equals("Невідвідував") && !student.getNationalGrade().equals("Невідвідувала"))) {
                    statementStudentError.getTotalGradeErrorText().add("Не вказано загальну оцінку");
                } else if (student.getTotalGrade() != null && student.getEctsGrade()!=null && ((student.getTotalGrade() >= 91 && !student.getEctsGrade().equals("A"))
                        || (student.getTotalGrade() >= 81 && !student.getEctsGrade().equals("B") && student.getTotalGrade() <= 90)
                        || (student.getTotalGrade() >= 71 && !student.getEctsGrade().equals("C") && student.getTotalGrade() <= 80)
                        || (student.getTotalGrade() >= 66 && !student.getEctsGrade().equals("D") && student.getTotalGrade() <= 70)
                        || (student.getTotalGrade() >= 60 && !student.getEctsGrade().equals("E") && student.getTotalGrade() <= 65)
                        || (student.getTotalGrade() < 60 && !student.getEctsGrade().equals("F")))) {
                    statementStudentError.getEctsGradeErrorText().add("Оцінка за ЄКТС не відповідає загальній оцінці");
                }
            } else if (student.getTotalGrade() == null) {
                statementStudentError.getTotalGradeErrorText().add("Не вказано загальну оцінку");
            }


            if (student.getNationalGrade() == null) {
                statementStudentError.getNationalGradeErrorText().add("Не вказано оцінку за нац. шкалою");
            } else if (!student.getNationalGrade().equals("Добре") && !student.getNationalGrade().equals("Задовільно") && !student.getNationalGrade().equals("Незадовільно") && !student.getNationalGrade().equals("Відмінно") && !student.getNationalGrade().equals("Зараховано") && !student.getNationalGrade().equals("Незараховано") && !student.getNationalGrade().equals("Недопущений") && !student.getNationalGrade().equals("Недопущена") && !student.getNationalGrade().equals("Недопущено") && !student.getNationalGrade().equals("Незараховано") && !student.getNationalGrade().equals("Незарахована") && !student.getNationalGrade().equals("Незарахований") && !student.getNationalGrade().equals("Невідвідував") && !student.getNationalGrade().equals("Невідвідувала")) {
                statementStudentError.getNationalGradeErrorText().add("Неправильно вказано формат оцінки за нац. шкалою");
            } else if (student.getTotalGrade() != null) {
                if ((student.getTotalGrade() >= 91 && (!student.getNationalGrade().equals("Відмінно") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() >= 81 && student.getTotalGrade() <= 90 && (!student.getNationalGrade().equals("Добре") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() >= 71 && student.getTotalGrade() <= 80 && (!student.getNationalGrade().equals("Добре") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() >= 66 && student.getTotalGrade() <= 70 && (!student.getNationalGrade().equals("Задовільно") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() >= 60 && student.getTotalGrade() <= 65 && (!student.getNationalGrade().equals("Задовільно") && !student.getNationalGrade().equals("Зараховано")))
                        || (student.getTotalGrade() < 60 && (!student.getNationalGrade().equals("Незараховано") && !student.getNationalGrade().equals("Недопущений") && !student.getNationalGrade().equals("Незадовільно") && !student.getNationalGrade().equals("Недопущена") && !student.getNationalGrade().equals("Недопущено") && !student.getNationalGrade().equals("Незараховано") && !student.getNationalGrade().equals("Незарахована") && !student.getNationalGrade().equals("Незарахований") && !student.getNationalGrade().equals("Невідвідував") && !student.getNationalGrade().equals("Невідвідувала")))) {
                    statementStudentError.getNationalGradeErrorText().add("Оцінка за національною шкалою не відповідає загальній оцінці");
                }
            }


            if (student.getEctsGrade() == null) {
                statementStudentError.getEctsGradeErrorText().add("Не вказано оцінку у системі ЄКТС");
            } else if (!student.getEctsGrade().equals("A") && !student.getEctsGrade().equals("B") && !student.getEctsGrade().equals("C") && !student.getEctsGrade().equals("D") && !student.getEctsGrade().equals("E") && !student.getEctsGrade().equals("F")) {
                statementStudentError.getEctsGradeErrorText().add("Не вірно вказан формат оцінки у ЄКТС");
            }
//            if (student.getSemesterGrade() != null && student.getControlGrade() != null && student.getTotalGrade() != null) {
//                if (student.getSemesterGrade() + student.getControlGrade() != student.getTotalGrade()) {
//                    statementStudentError.getTotalGradeErrorText().add("Неправильно порахована сума оцінок за семестр і за екзамен/залік");
//                }
//                //if (student.getTotalGrade() > 60 && student.getNationalGrade())
//            }

            statementStudentErrorsMap.getStatementStudentsErrorsMap().put(student.getStudentId(), statementStudentError);
        }
        return statementStudentErrorsMap;
    }
}
