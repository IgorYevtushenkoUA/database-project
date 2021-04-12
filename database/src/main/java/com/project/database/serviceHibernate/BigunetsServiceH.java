package com.project.database.serviceHibernate;

import com.project.database.dto.bigunets.BigunetsReport;
import com.project.database.entities.BigunetsEntity;
import com.project.database.parser.parserBigunets.BigunetsParser;
import com.project.database.repository.BigunetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class BigunetsServiceH {

    private final BigunetsRepository bigunetsRepository;

    public Page<List<String>> findAllStudentBigunets(int studentCode, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page-1, numberPerPage);
        return bigunetsRepository.findAllStudentBigunets(studentCode, pageable);
    }

    public void insertBigunets(BigunetsEntity bigunets) {
        if (bigunetsRepository.findByBigunetsNo(bigunets.getBigunetsNo()) == null) {
            bigunetsRepository.save(bigunets);
        }
    }

    public Page<BigunetsEntity> findAllByTutorNo(Integer tutorNo, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page-1, numberPerPage);
        return bigunetsRepository.findAllByTutorNo(tutorNo, pageable);
    }

    public Page<BigunetsEntity> findAllByGroupName(String groupName, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page-1, numberPerPage);
        return bigunetsRepository.findAllByGroupName(groupName, pageable);
    }

    public Page<BigunetsEntity> findAllBySubjectNo(Integer subjectNo, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page-1, numberPerPage);
        return bigunetsRepository.findAllBySubjectNo(subjectNo, pageable);
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


    public Integer saveBigunets(BigunetsReport bigunetsReport) {
        return null;
    }
}
