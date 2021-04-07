package com.project.database.controller;

import com.project.database.entity.Student;
import com.project.database.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/")
public class StudentsController {

    private final StudentService studentService;


    @Autowired
    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/students")
    public HashMap<String, Object> getAllStudents(
            @RequestParam(name = "year", defaultValue = "2020") String year,
            @RequestParam(name = "subject", required = false) String subject,
            @RequestParam(name = "tutor", required = false) String tutor,
            @RequestParam(name = "group", required = false) String group,
            @RequestParam(name = "semester", required = false) Integer semester,
            @RequestParam(name = "course", required = false) Integer course,
            @RequestParam(name = "sortBy", defaultValue = "rating") String sortBy, // {surname, rating}
            @RequestParam(name = "sortDesc", defaultValue = "true") Boolean sortDesc, // {high->low(desc);low->high(asc)}
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage
    ) {
        List<Student> students;
//        students = studentService.findAllByYearSubjectGroupTeacherTrimCourse(
//                year, subject, group, tutor,
//                String.valueOf(semester), String.valueOf(course),
//                sortBy, sortDesc, page, numberPerPage
//        );
        students = studentService.findAll(page, numberPerPage);
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", students);
        response.put("totalElements", studentService.findAll(1, Integer.MAX_VALUE).size()); // загальна кількість записів
        // потрібна
        return response;
    }


    @GetMapping("/debtors")
    public List<Student> getAllDebtors(
            @RequestParam(name = "year", defaultValue = "2020") String year,
            @RequestParam(name = "subject", required = false) String subject,
            @RequestParam(name = "tutor", required = false) String tutor,
            @RequestParam(name = "group", required = false) String group,
            @RequestParam(name = "semester", required = false) Integer semester,
            @RequestParam(name = "course", required = false) Integer course,
            @RequestParam(name = "sortBy", defaultValue = "rating") String sortBy, // {surname, rating}
            @RequestParam(name = "sortDesc", defaultValue = "true") Boolean sortDesc, // {high->low(desc);low->high(asc)}
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage
    ) {
        List<Student> debtors;
        debtors = studentService.findAllDebtorsByYearSubjectGroupTeacherTrimCourse(
                year, subject, group, tutor,
                String.valueOf(semester), String.valueOf(course),
                sortBy, sortDesc, page, numberPerPage
        );
        return debtors;
    }

}
