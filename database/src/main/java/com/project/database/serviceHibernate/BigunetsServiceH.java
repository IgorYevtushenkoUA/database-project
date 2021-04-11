package com.project.database.serviceHibernate;

import com.project.database.dto.bigunets.shortInfo.BigunetsShortInfo;
import com.project.database.entities.BigunetsEntity;
import com.project.database.repository.BigunetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        for (int i = 0; i < bigunetsShortInfos.size(); i++) {
            BigunetsShortInfo bsi = new BigunetsShortInfo();
            Object[] obj = list.get(i);
            int index = 1;
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

}
