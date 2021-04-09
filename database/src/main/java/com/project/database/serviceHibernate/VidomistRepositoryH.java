package com.project.database.serviceHibernate;

import com.project.database.entities.VidomistEntity;
import com.project.database.repository.VidomistMarkRepository;
import com.project.database.repository.VidomistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VidomistRepositoryH {
    private final VidomistRepository vidomistRepository;

    public List<List<String>> findAllStudentVidomosties(int studentCode) {
        return vidomistRepository.findAllStudentVidomosties(studentCode);
    }

    public void insertVidomist(VidomistEntity vidomist){
        if (vidomistRepository.findByVidomistNo(vidomist.getVidomistNo())==null){
            vidomistRepository.save(vidomist);
        }
    }
}
