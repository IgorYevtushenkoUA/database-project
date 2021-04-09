package com.project.database.controller;

import com.project.database.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class StudentController {
    private final StudentService studentService;


    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/student")
    public Object getStudentPIB(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "surname") String surname,
            @RequestParam(name = "patronymic") String patronymic,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage,
            Model model
    ) {

        model.addAttribute("name", name);
        model.addAttribute("surname", surname);
        model.addAttribute("patronymic", patronymic);
        model.addAttribute("page", page);
        model.addAttribute("numberPerPage", numberPerPage);
        return studentService.findByPIB(name, surname, patronymic, page, numberPerPage);
    }

    @PostMapping("/student")
    public Object postStudentPIB(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "surname") String surname,
            @RequestParam(name = "patronymic") String patronymic,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage,
            Model model
    ) {
        model.addAttribute("name", name);
        model.addAttribute("surname", surname);
        model.addAttribute("patronymic", patronymic);
        model.addAttribute("page", page);
        model.addAttribute("numberPerPage", numberPerPage);
        return studentService.findByPIB(name, surname, patronymic, page, numberPerPage);
    }

    @GetMapping("/student/{studentRecordBook}")
    public Object getStudentInfo(
            @PathVariable(name = "studentRecordBook") String studentRecordBook,
            @RequestParam(name = "finalMark", required = false) boolean finalMark, // хз що це (запиту немає на це)
            @RequestParam(name = "bigunets", required = false) boolean bigunets,
            @RequestParam(name = "averageMark", required = false) boolean averageMark,
            @RequestParam(name = "allMarks", required = false) boolean allMarks,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage
    ) {

        return studentService.findByStudentRecordBook(studentRecordBook);
    }

    @PostMapping("/student/{id}")
    public Object postStudentInfo(
            @PathVariable("id") int id,
            @RequestParam(name = "finalMark") boolean finalMark, // хз що це (запиту немає на це)
            @RequestParam(name = "bigunets") boolean bigunets,
            @RequestParam(name = "averageMark") boolean averageMark,
            @RequestParam(name = "allMarks") boolean allMarks,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage,
            Model model
    ) {

        model.addAttribute("id", id);
        model.addAttribute("finalMark", finalMark);
        model.addAttribute("bigunets", bigunets);
        model.addAttribute("averageMark", averageMark);
        model.addAttribute("allMarks", allMarks);
        model.addAttribute("page", page);
        model.addAttribute("numberPerPage", numberPerPage);

        if (finalMark) {
            // code
        } else if (bigunets) {
            return studentService.findAllBigunetsByStudentId(id, page, numberPerPage);
        } else if (averageMark) {
            return studentService.findAverageMarkById(id);
        } else if (allMarks) {
            return studentService.findAllMarksById(id, page, numberPerPage);
        }
        return 1;
    }

}
