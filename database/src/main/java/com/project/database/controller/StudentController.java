package com.project.database.controller;

import com.project.database.entity.Student;
import com.project.database.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class StudentController {

    private final StudentService studentService;


    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("students")
    public List<Student> getAll(
            @RequestParam(name = "year", defaultValue = "2020") String year,
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "tutor") String tutor,
            @RequestParam(name = "group") String group,
            @RequestParam(name = "trim") int trim,
            @RequestParam(name = "course") int course,
            @RequestParam(name = "studentType", defaultValue = "student") String studentType, // думав стосовно boolean (чи студент чи боржник)
            @RequestParam(name = "sortType", defaultValue = "surname") String sortType, // {surname, rating}
            @RequestParam(name = "sortGrow", defaultValue = "best") String sortGrow, // {high->low; low->high}
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage,
            Model model
    ) {
        model.addAttribute("year", year);
        model.addAttribute("subject", subject);
        model.addAttribute("tutor", tutor);
        model.addAttribute("group", group);
        model.addAttribute("trim", trim);
        model.addAttribute("course", course);
        model.addAttribute("studentType", studentType);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortGrow", sortGrow);

        return studentService.findAll(page, numberPerPage);
    }


    @PostMapping("/students")
    public List<Student> postAll(
            @RequestParam(name = "year", defaultValue = "2020") String year,
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "tutor") String tutor,
            @RequestParam(name = "group") String group,
            @RequestParam(name = "trim") int trim,
            @RequestParam(name = "course") int course,
            @RequestParam(name = "studentType", defaultValue = "student") String studentType,
            @RequestParam(name = "sortType", defaultValue = "surname") String sortType,
            @RequestParam(name = "sortGrow", defaultValue = "best") String sortGrow,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage
    ) {
        List<Student> students;
        students = studentType.equals("student")
                ? studentService.findAllByYearSubjectGroupTeacherTrimCourse(year, subject, group, tutor, String.valueOf(trim), String.valueOf(course), sortType, sortGrow, page, numberPerPage)
                : studentService.findAllDebtorsByYearSubjectGroupTeacherTrimCourse(year, subject, group, tutor, String.valueOf(trim), String.valueOf(course), sortType, sortGrow, page, numberPerPage);
        return students;
    }

}
