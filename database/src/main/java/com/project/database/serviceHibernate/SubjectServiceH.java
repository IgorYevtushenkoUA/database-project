package com.project.database.serviceHibernate;

import com.project.database.entities.SubjectEntity;
import com.project.database.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectServiceH {

    @Autowired
    private SubjectRepository subjectRepository;

    public void deleteBySubjectNo(int subjectId) {
        subjectRepository.deleteBySubjectNo(subjectId);
    }

    public Page<SubjectEntity> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    public List<SubjectEntity> findAll() {
        return subjectRepository.findAll();
    }

    public List<String> findAllSubjectNames() {
        return subjectRepository.findAllSubjectNames();
    }

    public List<String> findAllSubjectNames(String name) {
        return subjectRepository.findAllSubjectNames("%" + name + "%");
    }
}
