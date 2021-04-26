package com.project.database.controller;

import com.project.database.dto.bigunets.BigunetsReport;
import com.project.database.dto.bigunets.info.BigunetsInfo;
import com.project.database.dto.bigunets.shortInfo.BigunetsShortInfo;
import com.project.database.exception.StatementNotExist;
import com.project.database.serviceHibernate.BigunetsServiceH;
import com.project.database.serviceHibernate.ParserServiceH;
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
public class BigunetsController {

    private final BigunetsServiceH bigunetsService;
    private final ParserServiceH parserService;


    @GetMapping("/biguntsi")
    public Page<BigunetsShortInfo> getAll(
            @RequestParam(name = "subjectName", required = false) String subjectName,
            @RequestParam(name = "tutorName", required = false) String tutorName,
            @RequestParam(name = "group", required = false) String groupName,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "numberPerPage", defaultValue = "20") int numberPerPage
    ) {
        return bigunetsService.findAllBiguntsy(
                tutorName, subjectName, groupName,
                page, numberPerPage);
    }


    @GetMapping("/bigunets/{id}")
    public ResponseEntity<BigunetsInfo> getStatementInfo(@PathVariable("id") int id) {
        return ResponseEntity.ok(bigunetsService.findBigunetsById(id));
    }


    @PostMapping("/bigunets/process")
    public ResponseEntity<BigunetsReport> processStatementFile(
            @RequestParam("file") MultipartFile file
    ) {
        BigunetsReport bigunetsReport;
        try {
            bigunetsReport = bigunetsService.processBigunets(file);
            System.out.println(bigunetsReport);
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(bigunetsReport);
    }


    @PostMapping("/bigunets/save")
    public ResponseEntity<Integer> saveBigunets(
            @RequestBody BigunetsReport bigunetsReport
    ) {
        Integer bigunetsId;
        try {
            parserService.insertBigunets(bigunetsReport.getBigunetsInfo());
            bigunetsId = bigunetsReport.getBigunetsInfo().getBigunetsHeader().getBigunNo();
        } catch (StatementNotExist e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(bigunetsId);
    }
}
