package com.project.database.controller;

import com.project.database.entity.Student;
import com.project.database.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage
    ){
        return studentService.findAllStudents(page, numberPerPage);
    }
}
