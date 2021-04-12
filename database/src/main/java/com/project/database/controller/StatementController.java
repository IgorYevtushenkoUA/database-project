package com.project.database.controller;

import com.project.database.dto.statement.StatementReport;
import com.project.database.dto.statement.info.StatementInfo;
import com.project.database.serviceHibernate.StatementServiceH;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatementController {

    private final StatementServiceH statementService;


    @GetMapping("/statement/{id}")
    public ResponseEntity<StatementInfo> getStatementInfo(@PathVariable("id") int id) {
        return ResponseEntity.of(statementService.getStatementInfo(id));
    }

//    @GetMapping("/statements")
//    public

    @PostMapping("/statement/process")
    public ResponseEntity<StatementReport> processStatementFile(
            @RequestParam("file") MultipartFile file
    ) {
        StatementReport statementReport;
        try {
            statementReport = statementService.processStatement(file);
            System.out.println(statementReport);
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(statementReport);
    }

    @PostMapping("/statement/save")
    public ResponseEntity<Integer> saveStatement(
            @RequestBody StatementReport statementReport
    ) {
        Integer statementId;
        try {
            statementId = statementService.saveStatement(statementReport);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(statementId);
    }

//    @GetMapping("statement/{statementNo}/students")
//    public ResponseEntity<>

}
