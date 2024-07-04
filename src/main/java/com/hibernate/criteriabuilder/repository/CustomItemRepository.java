package com.hibernate.criteriabuilder.repository;

import com.hibernate.criteriabuilder.entity.Item;

import java.util.List;
import java.util.Map;

public interface CustomItemRepository {
    List<Item> findItemsByPrice(Integer price);
    List<Item> findItemsByNameAndPrice(String name, Integer price);

    List<Item> findItemsShouldHaveFutureExpiryDate();

    List<Item> findTopItemsByPrice(int limit);

    List<Object[]> findNoOfItemsByName();
}
