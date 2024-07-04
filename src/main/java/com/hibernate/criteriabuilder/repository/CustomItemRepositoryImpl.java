package com.hibernate.criteriabuilder.repository;

import com.hibernate.criteriabuilder.entity.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CustomItemRepositoryImpl implements CustomItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Item> findItemsByPrice(Integer price) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);
        Root<Item> root = cq.from(Item.class);
        Predicate pricePredicate = cb.equal(root.get("price"), price);
        cq.select(root).where(pricePredicate);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Item> findItemsByNameAndPrice(String name, Integer price) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);
        Root<Item> root = cq.from(Item.class);
        Predicate pricePredicate = cb.lessThanOrEqualTo(root.get("price"), price);
        Predicate namePredicate = cb.equal(root.get("name"), name);
        cq.select(root).where(cb.and(pricePredicate, namePredicate));
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Item> findItemsShouldHaveFutureExpiryDate() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);
        Root<Item> root = cq.from(Item.class);
        Predicate datePredicate = cb.greaterThan(root.get("date"), LocalDate.now());
        cq.select(root).where(datePredicate);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Item> findTopItemsByPrice(int limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);
        Root<Item> root = cq.from(Item.class);
        Order order = cb.asc(root.get("price"));
        cq.select(root).orderBy(order);
        return entityManager.createQuery(cq)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Object[]> findNoOfItemsByName() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Item> root = cq.from(Item.class);
        cq.multiselect(root.get("name"), cb.count(root.get("name")));
        cq.groupBy(root.get("name"));
        return entityManager
                .createQuery(cq)
                .getResultList();
    }
}
