package com.project.database.serviceHibernate;

import com.project.database.entities.GroupEntity;
import com.project.database.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceH {

    @Autowired
    private GroupRepository groupRepository;

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

    /**
     * @return [2020-2021, 2021-2022]
     */
    public List<String> findAllGroupEduYears() {
        return groupRepository.findAllGroupEduYears();
    }

    // insert
    public void insertGroup(GroupEntity group) {
        groupRepository.save(group);
    }

    // delete
    public void deleteGroupById(int groupCode) {
        groupRepository.deleteByGroupCode(groupCode);
    }

}
