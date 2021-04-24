package com.project.database.repository;

import com.project.database.entities.GroupEntity;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@RequiredArgsConstructor
public class StudentRepositoryCustomImpl implements StudentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> findTrims(String semestr) {
        // "select gr.trim from GroupEntity gr where gr.trim in (:trim)";
        String semestrStr = semestr == null
                ? " (select distinct(g.trim) from GroupEntity g) "
                : " (select distinct(g.trim) from GroupEntity g where g.trim = '" + semestr + "' ) ";

        // Outer query:
        // select trim from GroupEntity
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> cr = cb.createQuery(String.class);
        Root<GroupEntity> root = cr.from(GroupEntity.class);

        Subquery<String> sub = cr.subquery(String.class);
        Root<GroupEntity> subRoot = sub.from(GroupEntity.class);
        sub.select(subRoot.get("trim")).distinct(true);

        if (semestr != null) {
            sub.where(cb.equal(subRoot.get("trim"), semestr));
        }
        cr.select(root.get("trim")).where(root.get("trim").in(sub));

        TypedQuery<String> query = entityManager.createQuery(cr);
        List<String> resultList = query.getResultList();

        return resultList;
    }
}
