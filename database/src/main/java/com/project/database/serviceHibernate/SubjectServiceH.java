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

    /**
     *
     * @param pageable
     * @return [SubjectEntity(subjectNo=1, subjectName=БД, eduLevel=бакалавр, faculty=ФІ),...]
     */
    public Page<SubjectEntity> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    /**
     *
     * @return [SubjectEntity(subjectNo=1, subjectName=БД, eduLevel=бакалавр, faculty=ФІ),...]
     */
    public List<SubjectEntity> findAll() {
        return subjectRepository.findAll();
    }

    /**
     *
     * @return [БД, Веб-програмування, Вірусологія, Логічне програмування,...]
     */
    public List<String> findAllSubjectNames() {
        return subjectRepository.findAllSubjectNames();
    }

    /**
     *
     * @param name
     * @return [Веб-програмування, Логічне програмування, Моделювання інформаційних процесів]
     */
    public List<String> findAllSubjectNames(String name) {
        return subjectRepository.findAllSubjectNames("%" + name + "%");
    }

    // insert
    public void insertSubject(SubjectEntity subject){
        if (subjectRepository.findBySubjectName(subject.getSubjectName())==null){
            subjectRepository.save(subject);
        }
    }

    // delete
    public void deleteSubjectById(int subjectno){
        subjectRepository.deleteBySubjectNo(subjectno);
    }


}
