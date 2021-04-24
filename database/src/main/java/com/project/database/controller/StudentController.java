package com.project.database.controller;

import com.project.database.dto.bigunets.shortInfo.BigunetsShortInfo;
import com.project.database.dto.statement.shortInfo.StatementShortInfo;
import com.project.database.dto.subject.StudentSubjectShortInfo;
import com.project.database.entities.StudentEntity;
import com.project.database.serviceHibernate.BigunetsServiceH;
import com.project.database.serviceHibernate.StudentServiceH;
import com.project.database.serviceHibernate.VidomistServiceH;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceH studentService;
    private final VidomistServiceH statementService;
    private final BigunetsServiceH bigunetsService;


    @GetMapping("/student/{studentId}")
    public StudentEntity getStudentInfo(@PathVariable(name = "studentId") Integer studentId) {
        return studentService.findByStudentCode(studentId);
    }

    @GetMapping("/student/{studentId}/averageGrade")
    public Double getStudentAverage(
            @PathVariable(name = "studentId") Integer studentId,
            @RequestParam(name = "course") Integer course,
            @RequestParam(name = "semester") Integer semester) {
        Double studentAverageMarksForCourseTrim = studentService.findStudentAverageMarksForCourseTrim(studentId, course,
                semester);
        System.out.println(studentAverageMarksForCourseTrim);
        return studentAverageMarksForCourseTrim;
    }

    @GetMapping("/student/{studentId}/subjects")
    public Page<StudentSubjectShortInfo> getStudentSubjects(
            @PathVariable(name = "studentId") Integer studentId,
            @RequestParam(name = "course") Integer course,
            @RequestParam(name = "semester") String semester,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage
    ) {
        return studentService.findStudentMarks(studentId, course, semester, page, numberPerPage);
    }

    @GetMapping("/student/{studentId}/statements")
    public Page<StatementShortInfo> getStudentStatements(
            @PathVariable(name = "studentId") Integer studentId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage
    ) {
        return statementService.findAllStudentVidomosties(studentId, page, numberPerPage);
    }

    @GetMapping("/student/{studentId}/biguntsi")
    public Page<BigunetsShortInfo> getStudentBiguntsi(
            @PathVariable(name = "studentId") Integer studentId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage
    ) {
        return bigunetsService.findAllStudentBigunets(studentId, page, numberPerPage);
    }

}
