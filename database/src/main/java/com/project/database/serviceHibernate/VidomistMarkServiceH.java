package com.project.database.serviceHibernate;

import com.project.database.entities.VidomistMarkEntity;
import com.project.database.repository.VidomistMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VidomistMarkServiceH {

    private final VidomistMarkRepository vidomistMarkRepository;

    public Page<VidomistMarkEntity> findAllByStudentCode(int studentCode, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistMarkRepository.findAllByVidomistMarkIdStudentCode(studentCode, pageable);
    }

    public Page<VidomistMarkEntity> findAllByVidomistMarkIdVidomistNo(int vidomistNo, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistMarkRepository.findAllByVidomistMarkIdVidomistNo(vidomistNo, pageable);
    }

    public void insertVidomistMark(VidomistMarkEntity vidomistMark) {
        if (vidomistMarkRepository.findByVidomistMarkIdVidomistNoAndVidomistMarkIdStudentCode(
                vidomistMark.getVidomistMarkId().getVidomistNo(),
                vidomistMark.getVidomistMarkId().getStudentCode()) == null) {
            vidomistMarkRepository.save(vidomistMark);
        }
    }

    public void updateMark(VidomistMarkEntity vidomistMark) {
        VidomistMarkEntity updatedVidomist = vidomistMarkRepository.findByVidomistMarkIdVidomistNoAndVidomistMarkIdStudentCode(
                vidomistMark.getVidomistMarkId().getVidomistNo(), vidomistMark.getVidomistMarkId().getStudentCode());
        updatedVidomist.setTrimMark(vidomistMark.getTrimMark());
        updatedVidomist.setNatMark(vidomistMark.getNatMark());
        updatedVidomist.setMarkCheck(vidomistMark.getMarkCheck());
        updatedVidomist.setCompleteMark(vidomistMark.getCompleteMark());
        updatedVidomist.setEctsMark(vidomistMark.getEctsMark());
        vidomistMarkRepository.save(updatedVidomist);
    }

}
