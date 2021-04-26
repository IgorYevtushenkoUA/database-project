package com.project.database.serviceHibernate;

import com.project.database.entities.GroupEntity;
import com.project.database.entities.SubjectEntity;
import com.project.database.entities.TutorEntity;
import com.project.database.repository.GroupRepository;
import com.project.database.repository.SubjectRepository;
import com.project.database.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceH {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    /**
     * @return [GroupEntity(groupCode = 1, groupName = Група1, eduYear = 2020 - 2021, trim = 2, course = 3, subject = SubjectEntity ( subjectNo = 1, subjectName = БД, eduLevel = бакалавр, faculty = ФІ)),...]
     */
    public Page<GroupEntity> findAll(int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return groupRepository.findAll(pageable);
    }

    public void deleteByGroupCode(int groupId) {
        groupRepository.deleteByGroupCode(groupId);
    }

    public Page<GroupEntity> findAllByGroupName(int page, int numberPerPage) {
        Pageable pageable = PageRequest.of(page - 1, numberPerPage);
        return groupRepository.findAllByGroupName(pageable);
    }

    /**
     * @return [Група1, Група2, Група3, Група4]
     */
    public List<String> findAllGroupNames() {
        return groupRepository.findAllGroupNames();
    }

    public Page<String> findAllByEduYear(Pageable pageable) {
        return groupRepository.findAllByEduYear(pageable);
    }

    public List<String> findAllYears() {
        return groupRepository.findAllYears();
    }

    /**
     * @return [2020-2021, 2021-2022]
     */
    public List<String> findAllGroupEduYears() {
        return groupRepository.findAllGroupEduYears();
    }

    // insert
    public void insertGroup(GroupEntity group, SubjectEntity subject) {
        // тут треба перевірити за назвагрупи+назвапредмету+навчальний рік+семестр+курс
        if (findGroupByNameYearTrimCourseSubject(group, subject) == null) {
            groupRepository.save(group);
        }
    }

    // delete
    public void deleteGroupById(int groupCode) {
        groupRepository.deleteByGroupCode(groupCode);
    }

    public GroupEntity findGroupByNameYearTrimCourseSubject(GroupEntity group, SubjectEntity subject) {
        return groupRepository.findGroupByNameYearTrimCourseSubject(
                group.getGroupName(), group.getEduYear(), group.getTrim(), group.getCourse(), subject.getSubjectName());
    }

    public List<GroupEntity> findAllBySubjectName(String subjectName) {
        return groupRepository.findAllBySubjectName(subjectName);
    }

    public List<String> findAllGroupsByTeacherPIBAndSubjectName(String tutor, String subjet) {
        List<Integer> listTutor = getTutorList(tutor);
        List<String> listSubject = getSubjectList(subjet);
        return groupRepository.findAllGroupsByTeacherPIBAndSubjectName(listTutor, listSubject);
    }


    private List<Integer> getTutorList(String tutorName) {
        return tutorName == null || tutorName.isBlank()
                ? tutorRepository.findAll()
                .stream().map(TutorEntity::getTutorNo).distinct().collect(Collectors.toList())
                : tutorRepository.findAllTutorsByFullName(tutorName);
    }

    private List<String> getSubjectList(String subjectName) {
        return subjectName == null || subjectName.isBlank()
                ? subjectRepository.findAll()
                .stream().map(SubjectEntity::getSubjectName).distinct().collect(Collectors.toList())
                : subjectRepository.findDistinctBySubjectNameIn(Collections.singletonList(subjectName))
                .stream().map(SubjectEntity::getSubjectName).distinct().collect(Collectors.toList());
    }


}
