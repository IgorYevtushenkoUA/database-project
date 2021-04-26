package com.project.database.parser.parserBigunets.util;

//import parser.parserBigun.util.validators.DateValidator;
//import parser.parserBigun.util.validators.DateValidatorUsingDateTimeFormatter;

import com.project.database.parser.parserBigunets.util.validators.DateValidator;
import com.project.database.parser.parserBigunets.util.validators.DateValidatorUsingDateTimeFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class Util {
    public static Integer stringToInteger(String value) {
        return value == null ? null : Integer.parseInt(value);
    }

    public static LocalDate stringToDate(String date) {
        String dateStr = null;
        System.out.println("Due to: " + date + ".");
        if (date!=null) {
            date = date.replaceAll(" ", "");
            date = date.replaceAll("\n", "");
        }
        if (date != null && !date.equals("")) {
            String[] dateArr = date.split("#");
            switch (dateArr[1]) {
                case "січня":
                    dateArr[1] = "01";
                    break;
                case "лютого":
                    dateArr[1] = "02";
                    break;
                case "березня":
                    dateArr[1] = "03";
                    break;
                case "квітня":
                    dateArr[1] = "04";
                    break;
                case "травня":
                    dateArr[1] = "05";
                    break;
                case "червня":
                    dateArr[1] = "06";
                    break;
                case "липня":
                    dateArr[1] = "07";
                    break;
                case "серпня":
                    dateArr[1] = "08";
                    break;
                case "вересня":
                    dateArr[1] = "09";
                    break;
                case "жовтня":
                    dateArr[1] = "10";
                    break;
                case "листопада":
                    dateArr[1] = "11";
                    break;
                case "грудня":
                    dateArr[1] = "12";
                    break;
            }

            dateStr = dateArr[2] + "-" + dateArr[1] + "-" + dateArr[0];
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US)
                .withResolverStyle(ResolverStyle.STRICT);
        DateValidator validator = new DateValidatorUsingDateTimeFormatter(dateFormatter);


        return dateStr != null && validator.isValid(dateStr) ? LocalDate.parse(dateStr) : null;
    }

    public static String getTutorFullName(String tutorInfo) {
        String tutorFullName = null;
        if (tutorInfo != null) {
            String[] tutorI = tutorInfo.split(",");
            if (tutorI.length == 3) {
                tutorFullName = tutorI[0];
            } else if (tutorI.length == 2) {
                tutorFullName = tutorI[0];
            } else if (tutorI.length == 1)
                tutorFullName = tutorI[0];
        }
        return tutorFullName;
    }

    public static String getTutorAcademicStatus(String tutorInfo) {
        String tutorAcademicStatus = null;
        if (tutorInfo != null) {
            String[] tutorI = tutorInfo.split(",");
            if (tutorI.length == 3) {
                tutorAcademicStatus = tutorI[1];
                if (tutorAcademicStatus.charAt(0) == ' ') {
                    tutorAcademicStatus = tutorAcademicStatus.replaceFirst(" ", "");
                }
            } else if (tutorI.length == 2) {
                tutorAcademicStatus = tutorI[1];
                if (tutorAcademicStatus.charAt(0) == ' ')
                    tutorAcademicStatus = tutorAcademicStatus.replaceFirst(" ", "");
            }
        }
        return tutorAcademicStatus;
    }

    public static String getTutorPosition(String tutorInfo) {

        String tutorPosition = null;
        if (tutorInfo != null) {
            String[] tutorI = tutorInfo.split(",");
            if (tutorI.length == 3) {
                tutorPosition = tutorI[2];
                if (tutorPosition.charAt(0) == ' ') {
                    tutorPosition = tutorPosition.replaceFirst(" ", "");
                }
            }
        }
        return tutorPosition;
    }

//    void checkVidomist() {
//        if (getSheetId() == -1) {
//            System.out.println("Номер відомості неправильний.");
//        }
//        if (getFaculty() == null) {
//            System.out.println("Факультет вказано неправильно");
//        }
//        if (getEduLevel() == null || (!getEduLevel().equals("Бакалавр") && !getEduLevel().equals("Магістр"))) {
//            System.out.println("Освітній рівень вказано неправильно");
//        }
//        if (getStudyYear() == -1 || (getStudyYear() > 2 && getEduLevel().equals("Магістр")) || getStudyYear() > 4) {
//            System.out.println("Рік навчання введено неправильно");
//        }
//        if (getGroup() == null || (getGroup().length() > 3) && !getGroup().matches("[1-9]\\d?І")) {
//            System.out.println("Група введена неправильно.");
//        }
//        if (getSubject() == null) {
//
//            System.out.println("Предмет введений неправильно.");
//        }
//        if (getTerm() == null || (!getTerm().equals("1") && !getTerm().equals("2") && !getTerm().equals("3") && !getTerm().equals("4") && !getTerm().equals("5") && !getTerm().equals("6") && !getTerm().equals("7") && !getTerm().equals("8") && !getTerm().equals("2д") && !getTerm().equals("4д") && !getTerm().equals("6д") && !getTerm().equals("8д"))) {
//            System.out.println("Семестр введено неправильно");
//        }
//        if (getZalikoviBali() == -1) {
//            System.out.println("Залікові бали введено неправильно");
//        }
//        if (getFormControl() == null || (!getFormControl().equals("екзамен") && !getFormControl().equals("залік"))) {
//            System.out.println("Форма контролю введена неправильно");
//        }
//
//        String[] arr = getDate().split("#");
//        if (arr.length != 3)
//            System.out.println("Дата введена неправильно");
//        try {
//            Integer.parseInt(arr[0]);
//        } catch (Exception e) {
//            System.out.println("Дата введена неправильно");
//        }
//
//    }
}
