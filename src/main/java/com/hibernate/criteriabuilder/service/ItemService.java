package com.hibernate.criteriabuilder.service;

import com.hibernate.criteriabuilder.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface ItemService {

    Item save(Item item);

    void update(Item item);
    void delete(Integer id);
    List<Item> findItemsByPrice(Integer price);
    List<Item> findItemsByNameAndPrice(String name, Integer price);
    List<Item> findItemsBeforeExpiryDate();
    List<Item> findTopItemsByPrice(int limit);

    Map<String, Integer> findNoOfItemsByName();

    List<Item> findAllItems();
}
