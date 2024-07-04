package com.hibernate.criteriabuilder.service;

import com.hibernate.criteriabuilder.entity.Item;
import com.hibernate.criteriabuilder.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void update(Item item) {
        Optional<Item> optionalItem = itemRepository.findById(item.getId());
        if(optionalItem.isPresent())
        {
          itemRepository.save(item);
        }
    }
   @Override
   public void delete(Integer id)
   {
       itemRepository.deleteById(id);
   }

    @Override
    public List<Item> findItemsByPrice(Integer price) {
        return itemRepository.findItemsByPrice(price);
    }

    @Override
    public List<Item> findItemsByNameAndPrice(String name, Integer price) {
        return itemRepository.findItemsByNameAndPrice(name, price);
    }

    @Override
    public List<Item> findItemsBeforeExpiryDate() {
        return itemRepository.findItemsShouldHaveFutureExpiryDate();
    }

    @Override
    public List<Item> findTopItemsByPrice(int limit) {
        return itemRepository.findTopItemsByPrice(limit);
    }

    @Override
    public Map<String, Integer> findNoOfItemsByName() {
        List<Object[]> itemsArrays = itemRepository.findNoOfItemsByName();
        Map<String, Integer> itemsMap = new LinkedHashMap<>();
        itemsArrays.forEach(x -> {
            itemsMap.put(x[0].toString(), Integer.parseInt(x[1].toString()));

        });
        return itemsMap;
    }

    @Override
    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }
}
