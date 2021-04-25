package com.project.database.controller;

import com.project.database.serviceHibernate.GroupServiceH;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
