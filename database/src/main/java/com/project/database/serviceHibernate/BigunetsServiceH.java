package com.project.database.serviceHibernate;

import com.project.database.entities.BigunetsEntity;
import com.project.database.repository.BigunetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BigunetsServiceH {

    private final BigunetsRepository bigunetsRepository;

    public Page<List<String>> findAllStudentBigunets(int studentCode, String sortBy, boolean sortDesc, int page, int numberPerPage) {
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

}
