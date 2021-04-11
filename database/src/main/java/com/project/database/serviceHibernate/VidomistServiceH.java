package com.project.database.serviceHibernate;

import com.project.database.entities.VidomistEntity;
import com.project.database.repository.VidomistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VidomistServiceH {
    private final VidomistRepository vidomistRepository;

    public Page<List<String>> findAllStudentVidomosties(int studentCode, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistRepository.findAllStudentVidomosties(studentCode, pageable);
    }

    public void insertVidomist(VidomistEntity vidomist) {
        if (vidomistRepository.findByVidomistNo(vidomist.getVidomistNo()) == null) {
            vidomistRepository.save(vidomist);
        }
    }

    public void deleteVidomistById(int vidomistNo) {
        vidomistRepository.deleteById(vidomistNo);
    }

    public Page<VidomistEntity> findAllByTutorNo(Integer tutorNo, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistRepository.findAllByTutorNo(tutorNo, pageable);
    }

    public Page<VidomistEntity> findAllBySubjectNo(Integer subjectNo, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistRepository.findAllBySubjectNo(subjectNo, pageable);
    }

    public Page<VidomistEntity> findAllByGroupName(String groupName, int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return vidomistRepository.findAllByGroupName(groupName, pageable);
    }

    public int nonAdmissionByGroupCode(Integer groupCode) {
        return vidomistRepository.nonAdmissionByGroupCode(groupCode);
    }

    public int nonAdmissionBySubjectCode(Integer subjectNo) {
        return vidomistRepository.nonAdmissionBySubjectCode(subjectNo);
    }

    public int nonAdmissionByTeacherNo(Integer tutorNo) {
        return vidomistRepository.nonAdmissionByTeacherNo(tutorNo);
    }

    public VidomistEntity findVidomistNoByStudentRecordBookAndSubjectName(
            String studentRecordBook, String subjectName) {
        return vidomistRepository.findVidomistNoByStudentRecordBookAndSubjectName(studentRecordBook, subjectName);
    }

}
