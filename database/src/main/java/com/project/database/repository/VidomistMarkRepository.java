package com.project.database.repository;

import com.project.database.entities.VidomistMarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VidomistMarkRepository extends JpaRepository<VidomistMarkEntity, Integer> {

    List<VidomistMarkEntity> findAllByVidomistMarkIdStudentCode(int studentCode);

    List<VidomistMarkEntity> findAllByVidomistMarkIdVidomistNo(int vidomistNo);

    VidomistMarkEntity findByVidomistMarkIdVidomistNoAndVidomistMarkIdStudentCode(Integer vidomistNo, Integer StudentCode);
}
