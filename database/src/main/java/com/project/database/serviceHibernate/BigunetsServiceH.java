package com.project.database.serviceHibernate;

import com.project.database.dto.bigunets.BigunetsReport;
import com.project.database.dto.bigunets.shortInfo.BigunetsShortInfo;
import com.project.database.entities.BigunetsEntity;
import com.project.database.parser.parserBigunets.BigunetsParser;
import com.project.database.repository.BigunetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class BigunetsServiceH {

    private final BigunetsRepository bigunetsRepository;

    public Page<BigunetsShortInfo> findAllStudentBigunets(int studentCode, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        Page<Object[]> pageList = bigunetsRepository.findAllStudentBigunets(studentCode, pageable);
        return buildBigunetsShortInfo(pageList, pageable, (int) pageList.getTotalElements());
    }

    private Page<BigunetsShortInfo> buildBigunetsShortInfo(Page<Object[]> bigunetsP, Pageable pageable, int total) {
        List<BigunetsShortInfo> bigunetsShortInfos = new ArrayList<>();
        List<Object[]> list = bigunetsP.getContent();
        for (int i = 0; i < bigunetsP.getNumberOfElements(); i++) {
            BigunetsShortInfo bsi = new BigunetsShortInfo();
            Object[] obj = list.get(i);
            int index = 0;
            bsi.setStatementNo((Integer) obj[index++]);
            bsi.setTutorFullName((String) obj[index++] + " " + obj[index++] + " " + obj[index++]);
            bsi.setSubjectName((String) obj[index++]);
            bsi.setControlType((String) obj[index++]);
            bsi.setPostponeReason((String) obj[index++]);
            bsi.setExamDate((LocalDate) obj[index++]);
            bsi.setValidUntil((LocalDate) obj[index++]);

            bigunetsShortInfos.add(bsi);
        }
        return new PageImpl<>(bigunetsShortInfos, pageable, total);

    }

    public void insertBigunets(BigunetsEntity bigunets) {
        if (bigunetsRepository.findByBigunetsNo(bigunets.getBigunetsNo()) == null) {
            bigunetsRepository.save(bigunets);
        }
    }

    public Page<BigunetsShortInfo> findAllByTutorNo(Integer tutorNo, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        Page<Object[]> pageList = bigunetsRepository.findAllByTutorNo(tutorNo, pageable);
        return buildBigunetsShortInfo(pageList, pageable, (int) pageList.getTotalElements());
    }

    public Page<BigunetsShortInfo> findAllByGroupName(String groupName, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        Page<Object[]> pageList = bigunetsRepository.findAllByGroupName(groupName, pageable);
        return buildBigunetsShortInfo(pageList, pageable, (int) pageList.getTotalElements());
    }

    public Page<BigunetsShortInfo> findAllBySubjectNo(Integer subjectNo, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        Page<Object[]> pageList = bigunetsRepository.findAllBySubjectNo(subjectNo, pageable);
        return buildBigunetsShortInfo(pageList, pageable, (int) pageList.getTotalElements());
    }


    public BigunetsReport processBigunets(MultipartFile file) throws IOException {
        final Path statementFolder = Paths.get("biguntsi").toAbsolutePath().normalize();
        Files.createDirectories(statementFolder);
        Path targetLocation = statementFolder.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        //        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        file.transferTo(targetLocation);

        BigunetsParser parser = new BigunetsParser();
        return parser.getBigunReportByRoot(targetLocation);
    }


}
