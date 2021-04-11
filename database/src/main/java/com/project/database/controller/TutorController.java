package com.project.database.controller;

import com.project.database.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TutorController {

    private final TutorService tutorService;


    @Autowired
    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }


    @GetMapping("/tutorNames")
    public List<String> getAllTutorNames(){
        return tutorService.findAllTutorNames();
    }

}