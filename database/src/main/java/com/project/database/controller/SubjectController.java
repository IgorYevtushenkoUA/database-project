package com.project.database.controller;

import com.project.database.dto.subject.SubjectShortInfo;
import com.project.database.serviceHibernate.SubjectServiceH;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/subjects")
    public Page<SubjectShortInfo> findSubjectByName(
            @RequestParam(name = "subjectName") String subjectName,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "10") int numberPerPage
    ){
        return subjectService.findSubjectAverageMarkBySubjectName(subjectName, page, numberPerPage);
    }
}
