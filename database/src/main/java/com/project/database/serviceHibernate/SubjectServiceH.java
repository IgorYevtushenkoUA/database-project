package com.project.database.serviceHibernate;

import com.project.database.dto.subject.SubjectShortInfo;
import com.project.database.entities.SubjectEntity;
import com.project.database.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
     * @return [SubjectEntity(subjectNo = 1, subjectName = БД, eduLevel = бакалавр, faculty = ФІ), ...]
     */
    public Page<SubjectEntity> findAll(int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return subjectRepository.findAll(pageable);
    }

    /**
     * @return [БД, Веб-програмування, Вірусологія, Логічне програмування,...]
     */
    public List<String> findAllSubjectNames() {
        return subjectRepository.findAllSubjectNames();
    }

    /**
     * @param name
     * @return [Веб-програмування, Логічне програмування, Моделювання інформаційних процесів]
     */
    public List<String> findAllSubjectNames(String name) {
        return subjectRepository.findAllSubjectNames("%" + name + "%");
    }

    // insert
    public void insertSubject(SubjectEntity subject) {
        if (subjectRepository.findBySubjectName(subject.getSubjectName()) == null) {
            subjectRepository.save(subject);
        }
    }

    // delete
    public void deleteSubjectById(int subjectno) {
        subjectRepository.deleteBySubjectNo(subjectno);
    }


    public Page<SubjectShortInfo> findSubjectAverageMark(int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        Page<Object[]> pageList = subjectRepository.findSubjectAverageMark(pageable);
        return buildSubjectShortInfo(pageList, pageable, (int) pageList.getTotalElements());
    }

    private Page<SubjectShortInfo> buildSubjectShortInfo(Page<Object[]> subjectP, Pageable pageable, int total) {
        List<SubjectShortInfo> subjects = new ArrayList<>();
        List<Object[]> list = subjectP.getContent();
        for (int i = 0; i < subjectP.getNumberOfElements(); i++) {
            SubjectShortInfo subjectInfo = new SubjectShortInfo();
            Object[] obj = list.get(i);
            int index = 0;
            subjectInfo.setSubjectID((Integer) obj[index++]);
            subjectInfo.setSubjectName((String) obj[index++]);
            subjectInfo.setTutorFullName((String) obj[index++] + " " + obj[index++] + " " + obj[index++]);
            subjectInfo.setGrade((BigDecimal) obj[index++]);
            subjects.add(subjectInfo);
        }
        return new PageImpl<>(subjects, pageable, total);

    }

}
