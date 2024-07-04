package com.hibernate.criteriabuilder.controller;

import com.hibernate.criteriabuilder.entity.Item;
import com.hibernate.criteriabuilder.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/price/{price}")
    public ResponseEntity<List<Item>> getItemsByPrice(@PathVariable Integer price) {
        List<Item> items = itemService.findItemsByPrice(price);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Item> save(@RequestBody Item item) {
        Item savedItem = itemService.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Item item) {
        itemService.update(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity delete(Integer id) {
        itemService.delete(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Item>> getItems(@RequestParam(required = false) String name, @RequestParam(required = false) Integer price) {
        if (name == null || price == null) {
            return new ResponseEntity<>(itemService.findAllItems(), HttpStatus.OK);
        }
        return new ResponseEntity<>(itemService.findItemsByNameAndPrice(name, price), HttpStatus.OK);
    }

    @GetMapping("/non-expiry")
    public ResponseEntity<List<Item>> getItemsBeforeExpiryDate() {
        List<Item> items = itemService.findItemsBeforeExpiryDate();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/top-price")
    public ResponseEntity<List<Item>> getTopItemsByPrice(@RequestParam(required = true) Integer limit) {
        List<Item> items = itemService.findTopItemsByPrice(limit);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/count-by-name")
    public ResponseEntity<Map<String, Integer>> getNoOfItemsByName() {
        Map<String, Integer> ItemsCountPerName = itemService.findNoOfItemsByName();
        return new ResponseEntity<>(ItemsCountPerName, HttpStatus.OK);
    }
}