package com.project.database.controller;

import com.project.database.serviceHibernate.GroupServiceH;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/info")
public class InfoController {

    private final GroupServiceH groupService;

    @GetMapping("/years")
    List<String> getAllYears(){
        return groupService.findAllYears();
    }


    @GetMapping("/groups")
    List<String> getGroupNames(
            @RequestParam(value = "tutorName", required = false) String tutorFullName,
            @RequestParam(value = "subjectName", required = false) String subjectName
    ) {
        return groupService.findAllGroupsByTeacherPIBAndSubjectName(tutorFullName, subjectName);
    }
}
