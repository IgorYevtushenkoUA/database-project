package com.project.database.controller;

import com.project.database.serviceHibernate.TutorServiceH;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TutorController {

    private final TutorServiceH tutorServiceH;


    @GetMapping("/tutorNames")
    public List<String> getAllTutorNames(){
        return tutorServiceH.findAllTutorNames();
    }

}
