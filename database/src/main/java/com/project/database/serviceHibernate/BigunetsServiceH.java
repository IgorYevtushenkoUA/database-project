package com.project.database.serviceHibernate;

import com.project.database.dto.bigunets.BigunetsReport;
import com.project.database.dto.bigunets.shortInfo.BigunetsShortInfo;
import com.project.database.entities.BigunetsEntity;
import com.project.database.entities.GroupEntity;
import com.project.database.entities.SubjectEntity;
import com.project.database.entities.TutorEntity;
import com.project.database.parser.parserBigunets.BigunetsParser;
import com.project.database.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BigunetsServiceH {

    private final BigunetsRepository bigunetsRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final TutorRepository tutorRepository;

    public Page<BigunetsShortInfo> findAllBiguntsy(String tutorName, String subjectName, String groupName, int page, int numberPerPage) {
        List<String> subjectList = getSubjectList(subjectName);
        List<Integer> tutorList = getTutorList(tutorName);
        List<String> groupList = getGroupList(groupName);
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        Page<Object[]> pageList = bigunetsRepository.findAllBiguntsy(tutorList, subjectList, groupList, pageable);
        return buildBigunetsShortInfo(pageList, pageable, (int) pageList.getTotalElements());
    }

    public Page<BigunetsShortInfo> findAllStudentBigunets(int studentCode, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        Page<Object[]> pageList = bigunetsRepository.findAllStudentBigunets(studentCode, pageable);
        return buildBigunetsShortInfo(pageList, pageable, (int) pageList.getTotalElements());
    }

    public Page<BigunetsShortInfo> findAllBiguntsiBySubjectNameTutorNoGroupName(
            String subjectName, String tutorPIB, String groupName,
            int page, int numberPerPage) {
        Integer tutorNo = null;
        if (tutorPIB != null) {
            String[] pib = tutorPIB.split(" ");
            TutorEntity tutor = tutorRepository.findByTutorSurnameAndTutorNameAndTutorPatronymic(pib[0], pib[1], pib[2]);
            tutorNo = tutor == null ? null : tutor.getTutorNo();
        }
        List<String> subjectList = getSubjectList(subjectName);
        List<Integer> tutorList = getTutorList(tutorNo);
        List<String> groupList = getGroupList(groupName);

        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        Page<Object[]> pageList = bigunetsRepository.findAllBiguntsiBySubjectNameTutorNoGroupName(
                subjectList, tutorList, groupList, pageable);

        return buildBigunetsShortInfo(pageList, pageable, (int) pageList.getTotalElements());
    }

    private Page<BigunetsShortInfo> buildBigunetsShortInfo(Page<Object[]> bigunetsP, Pageable pageable, int total) {
        List<BigunetsShortInfo> bigunetsShortInfos = new ArrayList<>();
        List<Object[]> list = bigunetsP.getContent();
        for (int i = 0; i < bigunetsP.getNumberOfElements(); i++) {
//            BigunetsShortInfo bsi = new BigunetsShortInfo();
//            Object[] obj = list.get(i);
//            int index = 0;
//            bsi.setStatementNo((Integer) obj[index++]);
//            bsi.setTutorFullName((String) obj[index++] + " " + obj[index++] + " " + obj[index++]);
//            bsi.setSubjectName((String) obj[index++]);
//            bsi.setControlType((String) obj[index++]);
//            bsi.setPostponeReason((String) obj[index++]);
//            bsi.setExamDate((LocalDate) obj[index++]);
//            bsi.setValidUntil((LocalDate) obj[index++]);
//            bigunetsShortInfos.add(bsi);

            bigunetsShortInfos.add(setBigunetsShortInfo(list.get(i)));
        }
        return new PageImpl<>(bigunetsShortInfos, pageable, total);
    }

    private BigunetsShortInfo setBigunetsShortInfo(Object[] obj) {
        BigunetsShortInfo bsi = new BigunetsShortInfo();
        System.out.println(obj);

        int index = 0;
        bsi.setStatementNo((Integer) obj[index++]);
        bsi.setTutorFullName((String) obj[index++] + " " + obj[index++] + " " + obj[index++]);
        bsi.setSubjectName((String) obj[index++]);
        bsi.setControlType((String) obj[index++]);
        bsi.setPostponeReason((String) obj[index++]);
        bsi.setExamDate((LocalDate) obj[index++]);
        bsi.setValidUntil((LocalDate) obj[index++]);
        return bsi;
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


    private List<String> getSubjectList(String subjectName) {
        return subjectName == null
                ? subjectRepository.findAll()
                .stream().map(SubjectEntity::getSubjectName).distinct().collect(Collectors.toList())
                : subjectRepository.findDistinctBySubjectNameIn(Collections.singletonList(subjectName))
                .stream().map(SubjectEntity::getSubjectName).distinct().collect(Collectors.toList());
    }

    private List<String> getGroupList(String groupName) {
        return groupName == null
                ? groupRepository.findAll()
                .stream().map(GroupEntity::getGroupName).distinct().collect(Collectors.toList())
                : groupRepository.findDistinctAllByGroupNameIn(Collections.singletonList(groupName))
                .stream().map(GroupEntity::getGroupName).distinct().collect(Collectors.toList());
    }

    private List<Integer> getTutorList(Integer tutorNo) {
        return tutorNo == null
                ? tutorRepository.findAll()
                .stream().map(TutorEntity::getTutorNo).distinct().collect(Collectors.toList())
                : tutorRepository.findDistinctByTutorNoIn(Collections.singletonList(tutorNo))
                .stream().map(TutorEntity::getTutorNo).distinct().collect(Collectors.toList());
    }

    private List<Integer> getTutorList(String tutorName) {
        return tutorName == null
                ? tutorRepository.findAll()
                .stream().map(TutorEntity::getTutorNo).distinct().collect(Collectors.toList())
                : tutorRepository.findAllTutorsByFullName(tutorName);
    }

}
