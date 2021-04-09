package com.project.database.serviceHibernate;

import com.project.database.entities.VidomistMarkEntity;
import com.project.database.repository.VidomistMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VidomistMarkServiceH {

    private final VidomistMarkRepository vidomistMarkRepository;

    public List<VidomistMarkEntity> findAllByStudentCode(int studentCode) {
        return vidomistMarkRepository.findAllByVidomistMarkIdStudentCode(studentCode);
    }

    public List<VidomistMarkEntity> findAllByVidomistMarkIdVidomistNo(int vidomistNo) {
        return vidomistMarkRepository.findAllByVidomistMarkIdVidomistNo(vidomistNo);
    }

    public void insertVidomistMark(VidomistMarkEntity vidomistMark) {
        if (vidomistMarkRepository.findByVidomistMarkIdVidomistNoAndVidomistMarkIdStudentCode(vidomistMark.getVidomistMarkId().getVidomistNo(),
                vidomistMark.getVidomistMarkId().getStudentCode()) == null) {
            vidomistMarkRepository.save(vidomistMark);
        }
    }

}
