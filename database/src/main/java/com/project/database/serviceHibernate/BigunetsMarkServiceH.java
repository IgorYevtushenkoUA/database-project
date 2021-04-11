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

    public void insertBigunetsMark(BigunetsMarkEntity bigunetsMark) {
        bigunetsMarkRepository.save(bigunetsMark);
    }

    public void updateMark(BigunetsMarkEntity bigunetsMark) {
        BigunetsMarkEntity updatedBigunets = bigunetsMarkRepository.
                findByBigunetsMarkIdBigunetsNoAndBigunetsMarkIdStudentCodeAndBigunetsMarkIdVidomistNoAndBigunetsMarkIdTutorNo(
                        bigunetsMark.getBigunetsMarkId().getBigunetsNo(),
                        bigunetsMark.getBigunetsMarkId().getStudentCode(),
                        bigunetsMark.getBigunetsMarkId().getVidomistNo(),
                        bigunetsMark.getBigunetsMarkId().getTutorNo());

        updatedBigunets.setTrimMark(bigunetsMark.getTrimMark());
        updatedBigunets.setMarkCheck(bigunetsMark.getMarkCheck());
        updatedBigunets.setCompleteMark(bigunetsMark.getCompleteMark());
        updatedBigunets.setNatMark(bigunetsMark.getNatMark());
        updatedBigunets.setEctsMark(bigunetsMark.getEctsMark());

        bigunetsMarkRepository.save(updatedBigunets);
    }
}
