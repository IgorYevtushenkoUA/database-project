package com.project.database.controller;

import com.project.database.serviceHibernate.SubjectServiceH;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectServiceH subjectService;

    @GetMapping("/subjectNames")
    public List<String> getSubjectNames(){
        return subjectService.findAllSubjectNames();
    }
}
