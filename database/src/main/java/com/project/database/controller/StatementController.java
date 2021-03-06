package com.project.database.controller;

import com.project.database.dto.statement.StatementReport;
import com.project.database.dto.statement.info.StatementInfo;
import com.project.database.dto.statement.shortInfo.StatementShortInfo;
import com.project.database.serviceHibernate.ParserServiceH;
import com.project.database.serviceHibernate.StatementServiceH;
import com.project.database.serviceHibernate.VidomistServiceH;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final VidomistServiceH vidomistServiceH;
    private final ParserServiceH parserServiceH;


    @GetMapping("/statements")
    public Page<StatementShortInfo> getAll(
            @RequestParam(name = "subjectName", required = false) String subjectName,
            @RequestParam(name = "tutorName", required = false) String tutorName,
            @RequestParam(name = "group", required = false) String groupName,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage
    ) {
        return vidomistServiceH.findAllStatements(
                subjectName, tutorName, groupName,
                null, false,
                page, numberPerPage);
    }


    @GetMapping("/statement/{id}")
    public ResponseEntity<StatementInfo> getStatementInfo(@PathVariable("id") int id) {
        try {
            return ResponseEntity.of(statementService.getStatementInfo(id));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


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
            parserServiceH.insertVidomist(statementReport.getStatementInfo());
            statementId = statementReport.getStatementInfo().getStatementHeader().getStatementNo();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(statementId);
    }

}