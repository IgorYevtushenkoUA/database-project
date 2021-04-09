package com.project.database.serviceHibernate;

import com.project.database.entities.BigunetsEntity;
import com.project.database.repository.BigunetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BigunetsServiceH {

    private final BigunetsRepository bigunetsRepository;

    public Page<List<String>> findAllStudentBigunets(int studentCode, Pageable pageable){
        return bigunetsRepository.findAllStudentBigunets(studentCode, pageable);
    }

    public void insertBigunets(BigunetsEntity bigunets){
        if (bigunetsRepository.findByBigunetsNo(bigunets.getBigunetsNo())==null){
            bigunetsRepository.save(bigunets);
        }
    }



}
