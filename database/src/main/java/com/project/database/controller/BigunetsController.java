package com.project.database.controller;

import com.project.database.dto.bigunets.BigunetsReport;
import com.project.database.serviceHibernate.BigunetsServiceH;
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
public class BigunetsController {

    private final BigunetsServiceH bigunetsService;

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

    @PostMapping("/statement/save")
    public ResponseEntity<Integer> saveStatement(
            @RequestBody BigunetsReport bigunetsReport
    ) {
        Integer bigunetsId;
        try {
            bigunetsId = bigunetsService.saveBigunets(bigunetsReport);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(bigunetsId);
    }
}
