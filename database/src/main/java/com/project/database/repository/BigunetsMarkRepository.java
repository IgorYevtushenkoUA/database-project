package com.project.database.repository;

import com.project.database.entities.BigunetsMarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BigunetsMarkRepository extends JpaRepository<BigunetsMarkEntity, Integer> {

    BigunetsMarkEntity findByBigunetsMarkIdBigunetsNoAndBigunetsMarkIdStudentCodeAndBigunetsMarkIdVidomistNoAndBigunetsMarkIdTutorNo(
        Integer bigunetsNo,
        Integer studentCode,
        Integer vidomistNo,
        Integer tutorNo
    );

}
