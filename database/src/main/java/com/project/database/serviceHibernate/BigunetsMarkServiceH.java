package com.project.database.serviceHibernate;

import com.project.database.entities.BigunetsMarkEntity;
import com.project.database.repository.BigunetsMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BigunetsMarkServiceH {
    private final BigunetsMarkRepository bigunetsMarkRepository;

    public void insertBigunetsMark(BigunetsMarkEntity bigunetsMark){
        bigunetsMarkRepository.save(bigunetsMark);
    }
}
