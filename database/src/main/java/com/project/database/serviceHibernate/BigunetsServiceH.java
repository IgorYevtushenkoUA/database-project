package com.project.database.serviceHibernate;

import com.project.database.entities.BigunetsEntity;
import com.project.database.repository.BigunetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BigunetsServiceH {

    private final BigunetsRepository bigunetsRepository;

    public List<List<String>> findAllStudentBigunets(int studentCode){
        return bigunetsRepository.findAllStudentBigunets(studentCode);
    }

}
