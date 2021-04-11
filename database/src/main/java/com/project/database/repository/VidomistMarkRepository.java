package com.project.database.repository;

import com.project.database.entities.VidomistMarkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VidomistMarkRepository extends JpaRepository<VidomistMarkEntity, Integer> {

    Page<VidomistMarkEntity> findAllByVidomistMarkIdStudentCode(int studentCode, Pageable pageable);

    Page<VidomistMarkEntity> findAllByVidomistMarkIdVidomistNo(int vidomistNo, Pageable pageable);

    VidomistMarkEntity findByVidomistMarkIdVidomistNoAndVidomistMarkIdStudentCode(Integer vidomistNo, Integer StudentCode);
}
