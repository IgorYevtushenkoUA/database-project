package com.project.database.controller;

import com.project.database.dto.student.StudentShortInfo;
import com.project.database.serviceHibernate.StudentServiceH;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class StudentsController {

    private final StudentServiceH studentServiceH;


    @GetMapping("/students")
    public Page<StudentShortInfo> getAllStudents(
            @RequestParam(name = "year", required = false) String year,
            @RequestParam(name = "subject", required = false) String subject,
            @RequestParam(name = "tutor", required = false) String tutor,
            @RequestParam(name = "group", required = false) String group,
            @RequestParam(name = "semester", required = false) Integer semester,
            @RequestParam(name = "course", required = false) Integer course,
            @RequestParam(name = "sortBy", defaultValue = "student_surname") String sortBy, // {surname, rating}
            @RequestParam(name = "sortDesc", defaultValue = "true") Boolean sortDesc, // {high->low(desc);low->high(asc)}
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage
    ) {
        Page<StudentShortInfo> students;
        students = studentServiceH.findStudentsWithRating(
                year, subject, tutor, group,
                semester, course,
                sortBy, sortDesc, page, numberPerPage
        );
        return students;
    }


    @GetMapping("/debtors")
    public Page<StudentShortInfo> getAllDebtors(
            @RequestParam(name = "year", required = false) String year,
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
        return studentServiceH.findDebtorsRatingDefault(
                year, group, semester, course, subject, null,
                sortBy, sortDesc, page, numberPerPage
        );
    }

}
