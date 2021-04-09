package com.project.database.serviceHibernate;

import com.project.database.entities.GroupEntity;
import com.project.database.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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
    public List<GroupEntity> findAll() {
        return groupRepository.findAll();
    }

    public void deleteByGroupCode(int groupId) {
        groupRepository.deleteByGroupCode(groupId);
    }

    public Page<GroupEntity> findAllByGroupName(Pageable pageable) {
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
    public void insertGroup(GroupEntity group){
        groupRepository.save(group);
    }

    // delete
    public void deleteGroupById(int groupCode){
        groupRepository.deleteByGroupCode(groupCode);
    }

}
