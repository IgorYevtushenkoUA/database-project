package com.project.database.parser.parserStatement;

import com.project.database.dto.statement.StatementsReport;
import com.project.database.dto.statement.errors.StatementErrors;
import com.project.database.dto.statement.info.StatementFooter;
import com.project.database.dto.statement.info.StatementHeader;
import com.project.database.dto.statement.info.StatementInfo;
import com.project.database.dto.statement.info.StatementStudent;
import com.project.database.parser.parserStatement.util.validators.StatementValidator;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.project.database.parser.parserStatement.util.Util.*;


public class StatementParser {

    public static String convertPDFtoStr(String root) throws IOException, URISyntaxException {
        URL url = StatementParser.class.getResource(root);
        File f = new File(url.toURI());
        String parsedText;
        PDFParser parser = new PDFParser(new RandomAccessFile(f, "r"));
        parser.parse();

        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        parsedText = pdfStripper.getText(pdDoc);
        String allFileString = "";

        for (int i = 0; i < parsedText.length(); i++) {
            if (parsedText.charAt(i) != '_') {  // or anything other character you chose
                allFileString += parsedText.charAt(i);
            }

        }
        return allFileString;
    }


    public static void main(String[] args) throws IOException {
        StatementParser parser = new StatementParser();

        StatementsReport statementsReport = parser.getStatementsReportByRoot("/pdfs/ios_wrong_sumDONE.pdf");
        System.out.println(statementsReport);
    }


    public Map<Enums.Element, String> parse(String allFileString) {
        Map<Enums.Element, String> map = new HashMap();

        for (Enums.Element e : Enums.Element.values()) {
            if (e.equals(Enums.Element.STUDENT))
                continue;
            Pattern pattern = Pattern.compile(e.regex);
            Matcher matcher = pattern.matcher(allFileString);
            while (matcher.find()) {
                String res = "";
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    if (i > 1) res += '#';
                    res += matcher.group(i);
                }
                map.put(e, res);
            }
        }
        return map;
    }


    public List<StatementStudent> getStudents(String allFileString) {
        List<StatementStudent> students = new ArrayList();
        System.out.println(allFileString);
        Pattern pattern = Pattern.compile(Enums.Element.STUDENT.regex);
        Matcher matcher = pattern.matcher(allFileString);
        while (matcher.find()) {
            String res = "";
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (i > 1) res += '#';
                res += matcher.group(i).replaceAll("[\n\r]+", "");
            }
            students.add(parseStudent(res));
        }
        return students;
    }


    private StatementStudent parseStudent(String studentInfo) {
        StatementStudent.StatementStudentBuilder statementStudentBuilder = StatementStudent.builder();
        String surname = null;
        if (studentInfo.length() != 0) {
            String informationAboutStudent = " " + studentInfo.replaceAll("#", "# ");
            String[] studentInfos = informationAboutStudent.split("#");
            for (int i = 0; i < studentInfos.length; i++) {
                String value = " ".equals(studentInfos[i]) ? null : studentInfos[i].substring(1);

                switch (i) {
                    case 0:
                        statementStudentBuilder.studentId(stringToInteger(value));
                        break;
                    case 1:
                        surname = value;
                        break;
                    case 2:
                        statementStudentBuilder.studentPI(surname + " " + value);
                        break;
                    case 3:
                        statementStudentBuilder.studentPatronymic(value);
                        break;
                    case 4:
                        statementStudentBuilder.studentRecordBook(value);
                        break;
                    case 5:
                        statementStudentBuilder.semesterGrade(stringToInteger(value));
                        break;
                    case 6:
                        statementStudentBuilder.controlGrade(stringToInteger(value));
                        break;
                    case 7:
                        statementStudentBuilder.totalGrade(stringToInteger(value));
                        break;
                    case 8:
                        if (value != null && value.contains(" ")){
                           value = value.replaceAll(" ", "");
                        }
                            statementStudentBuilder.nationalGrade(value);
                        break;
                    case 9:
                        statementStudentBuilder.ectsGrade(value);
                        break;
                    default:
                        throw new RuntimeException("Something went wrong " + studentInfo);
                }
            }
        }
        return statementStudentBuilder.build();
    }


    // get StatementsReport by its location
    public StatementsReport getStatementsReportByRoot(String root) {

        String allFileString = null;
        try {
            allFileString = convertPDFtoStr(root);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return getStatementsReport(allFileString);
    }

    //get StatementsReport by its String representation
    public StatementsReport getStatementsReport(String allFileString) {
        Map<Enums.Element, String> map = parse(allFileString);
        List<StatementStudent> students = getStudents(allFileString);
        return getStatementsReport(map, students);
    }

    //get StatementsReport by its map and student list representation
    public StatementsReport getStatementsReport(Map<Enums.Element, String> map, List<StatementStudent> students) {


        StatementsReport.StatementsReportBuilder statementsReportBuilder = StatementsReport.builder();
        //Footer building
        StatementFooter statementFooter = StatementFooter
                .builder()
                .absentCount(stringToInteger(map.get(Enums.Element.ABSENT_NUMBER)))
                .presentCount(stringToInteger(map.get(Enums.Element.PRESENT_NUMBER)))
                .rejectedCount(stringToInteger(map.get(Enums.Element.FAILURE_NUMBER)))
                .build();

        //Header building
        StatementHeader statementHeader = StatementHeader
                .builder()
                .statementNo(stringToInteger(map.get(Enums.Element.SHEET_ID)))
                .controlType(map.get(Enums.Element.FORM_CONTROL))
                .faculty(map.get(Enums.Element.FACULTY))
                .course(stringToInteger(map.get(Enums.Element.STUDY_YEAR)))
                .group(map.get(Enums.Element.GROUP))
                .eduLevel(map.get(Enums.Element.EDU_LEVEL))
                .semester(map.get(Enums.Element.TERM))
                .creditNumber(map.get(Enums.Element.ZALIK_BALI))
                .examDate(stringToDate(map.get(Enums.Element.DATE)))
                .subjectName(map.get(Enums.Element.SUBJECT))
                .tutorPosition(getTutorPosition(map.get(Enums.Element.TUTOR_INFO)))
                .tutorAcademicStatus(getTutorAcademicStatus(map.get(Enums.Element.TUTOR_INFO)))
                .tutorFullName(getTutorFullName(map.get(Enums.Element.TUTOR_INFO)))
                .build();

        //BigunetsInfo building
        StatementInfo statementInfo = StatementInfo
                .builder()
                .statementStudents(students)
                .statementFooter(statementFooter)
                .statementHeader(statementHeader)
                .build();

        //StatementErrors building
        StatementValidator statementValidator = new StatementValidator();
        StatementErrors statementErrors = StatementErrors
                .builder()
                .footerErrors(statementValidator.getFooterErrors(statementFooter))
                .headerErrors(statementValidator.getHeaderErrors(statementHeader))
                .studentErrorsMap(statementValidator.getStudentErrors(students))
                .build();

        //StatementsReport building
        StatementsReport statementsReport = StatementsReport
                .builder()
                .statementInfo(statementInfo)
                .statementErrors(statementErrors)
                .build();

        return statementsReport;
    }
}
