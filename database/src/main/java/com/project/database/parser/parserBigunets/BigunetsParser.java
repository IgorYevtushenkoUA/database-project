package com.project.database.parser.parserBigunets;

import com.project.database.dto.bigunets.BigunetsReport;
import com.project.database.dto.bigunets.errors.BigunetsErrors;
import com.project.database.dto.bigunets.info.BigunetsHeader;
import com.project.database.dto.bigunets.info.BigunetsInfo;
import com.project.database.dto.bigunets.info.BigunetsStudent;
import com.project.database.parser.parserBigunets.util.validators.BigunValidator;
import com.project.database.parser.parserStatement.StatementParser;
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

import static com.project.database.parser.parserBigunets.util.Util.*;


public class BigunetsParser {

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
        BigunetsParser parser = new BigunetsParser();
        BigunetsReport bigunetsReport = parser.getBigunReportByRoot("/pdfs/unix_bigunetsDONE.pdf");
        System.out.println(bigunetsReport);
    }


    public Map<Enums.BigunElement, String> parse(String allFileString) {
        Map<Enums.BigunElement, String> map = new HashMap();

        for (Enums.BigunElement e : Enums.BigunElement.values()) {
            if (e.equals(Enums.BigunElement.STUDENT))
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


    public List<BigunetsStudent> getStudents(String allFileString) {
        List<BigunetsStudent> students = new ArrayList();
        System.out.println(allFileString);
        Pattern pattern = Pattern.compile(Enums.BigunElement.STUDENT.regex);
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


    private BigunetsStudent parseStudent(String studentInfo) {
        BigunetsStudent.BigunetsStudentBuilder bigunStudentBuilder = BigunetsStudent.builder();
        String surname = null;
        if (studentInfo.length() != 0) {
            String informationAboutStudent = " " + studentInfo.replaceAll("#", "# ");
            String[] studentInfos = informationAboutStudent.split("#");
            for (int i = 0; i < studentInfos.length; i++) {
                String value = " ".equals(studentInfos[i]) ? null : studentInfos[i].substring(1);

                switch (i) {
                    case 0:
                        bigunStudentBuilder.studentId(stringToInteger(value));
                        break;
                    case 1:
                        surname = value;
                        break;
                    case 2:
                        bigunStudentBuilder.studentPI(surname + " " + value);
                        break;
                    case 3:
                        bigunStudentBuilder.studentPatronymic(value);
                        break;
                    case 4:
                        bigunStudentBuilder.studentRecordBook(value);
                        break;
                    case 5:
                        bigunStudentBuilder.semesterGrade(stringToInteger(value));
                        break;
                    case 6:
                        bigunStudentBuilder.controlGrade(stringToInteger(value));
                        break;
                    case 7:
                        bigunStudentBuilder.totalGrade(stringToInteger(value));
                        break;
                    case 8:
                        if (value != null && value.contains(" ")){
                           value = value.replaceAll(" ", "");
                        }
                        bigunStudentBuilder.nationalGrade(value);
                        break;
                    case 9:
                        bigunStudentBuilder.ectsGrade(value);
                        break;
                    default:
                        throw new RuntimeException("Something went wrong " + studentInfo);
                }
            }
        }
        return bigunStudentBuilder.build();
    }


    // get StatementsReport by its location
    public BigunetsReport getBigunReportByRoot(String root) {

        String allFileString = null;
        try {
            allFileString = convertPDFtoStr(root);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return getBigunReport(allFileString);
    }

    //get StatementsReport by its String representation
    public BigunetsReport getBigunReport(String allFileString) {
        Map<Enums.BigunElement, String> map = parse(allFileString);
        List<BigunetsStudent> students = getStudents(allFileString);
        return getBigunReport(map, students);
    }

    //get StatementsReport by its map and student list representation
    public BigunetsReport getBigunReport(Map<Enums.BigunElement, String> map, List<BigunetsStudent> students) {


        BigunetsReport.BigunetsReportBuilder bigunReportBuilder = BigunetsReport.builder();

        //Header building
        BigunetsHeader bigunetsHeader = BigunetsHeader
                .builder()
                .bigunNo(stringToInteger(map.get(Enums.BigunElement.SHEET_ID)))
                .controlType(map.get(Enums.BigunElement.FORM_CONTROL))
                .faculty(map.get(Enums.BigunElement.FACULTY))
                .dueTo(stringToDate(map.get(Enums.BigunElement.DUE_TO)))
                .postponeReason(map.get(Enums.BigunElement.POSTPONE_REASON))
                .course(stringToInteger(map.get(Enums.BigunElement.STUDY_YEAR)))
                .group(map.get(Enums.BigunElement.GROUP))
                .eduLevel(map.get(Enums.BigunElement.EDU_LEVEL))
                .semester(map.get(Enums.BigunElement.TERM))
                .creditNumber(map.get(Enums.BigunElement.ZALIK_BALI))
                .examDate(stringToDate(map.get(Enums.BigunElement.DATE)))
                .subjectName(map.get(Enums.BigunElement.SUBJECT))
                .tutorPosition(getTutorPosition(map.get(Enums.BigunElement.TUTOR_INFO)))
                .tutorAcademicStatus(getTutorAcademicStatus(map.get(Enums.BigunElement.TUTOR_INFO)))
                .tutorFullName(getTutorFullName(map.get(Enums.BigunElement.TUTOR_INFO)))
                .build();

        //BigunetsInfo building
        BigunetsInfo bigunetsInfo = BigunetsInfo
                .builder()
                .bigunetsStudents(students)
                .bigunetsHeader(bigunetsHeader)
                .build();

        //StatementErrors building
        BigunValidator bigunValidator = new BigunValidator();
        BigunetsErrors bigunetsErrors = BigunetsErrors
                .builder()
                .headerErrors(bigunValidator.getHeaderErrors(bigunetsHeader))
                .studentErrorsMap(bigunValidator.getStudentErrors(students))
                .build();

        //StatementsReport building
        BigunetsReport bigunetsReport = BigunetsReport
                .builder()
                .bigunetsInfo(bigunetsInfo)
                .bigunetsErrors(bigunetsErrors)
                .build();

        return bigunetsReport;
    }
}
