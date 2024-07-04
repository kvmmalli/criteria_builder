package com.hibernate.criteriabuilder.repository;


import com.hibernate.criteriabuilder.entity.Item;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


public class ItemRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery criteriaQuery;

    @Mock
    private CriteriaQuery<Object[]> criteriaObjectArrayQuery;

    @Mock
    private Root root;

    @Mock
    private TypedQuery<Item> typedQuery;

    @Mock
    private Path<Long> path;

    @InjectMocks
    private CustomItemRepositoryImpl customItemRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindItemsByPrice() {
        // Mock data
        Integer price = 100;
        List<Item> mockItems = Arrays.asList(new Item(), new Item());

        // Mock EntityManager methods
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Item.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Item.class)).thenReturn(root);
        when(criteriaBuilder.equal(root.get("price"), price)).thenReturn(mock(Predicate.class));
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mockItems);

        // Call repository method
        List<Item> result = customItemRepository.findItemsByPrice(price);

        // Verify
        assertEquals(mockItems.size(), result.size());
        verify(entityManager, times(1)).createQuery(criteriaQuery);
    }

    @Test
    public void testFindItemsByNameAndPrice() {
        // Mock data
        String name = "Test Item";
        Integer price = 100;
        List<Item> mockItems = Arrays.asList(new Item(), new Item());

        // Mock EntityManager methods
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Item.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Item.class)).thenReturn(root);
        when(criteriaBuilder.equal(root.get("name"), name)).thenReturn(mock(Predicate.class));
        when(criteriaBuilder.lessThanOrEqualTo(root.get("price"), price)).thenReturn(mock(Predicate.class));
        when(criteriaBuilder.and(any(Predicate[].class))).thenReturn(mock(Predicate.class));
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mockItems);

        // Call repository method
        List<Item> result = customItemRepository.findItemsByNameAndPrice(name, price);

        // Verify
        assertEquals(mockItems.size(), result.size());
        verify(entityManager, times(1)).createQuery(criteriaQuery);
    }

    @Test
    public void testFindItemsShouldHaveFutureExpiryDate() {
        // Mock data
        List<Item> mockItems = Arrays.asList(new Item(), new Item());

        // Mock EntityManager methods
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Item.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Item.class)).thenReturn(root);
        when(criteriaBuilder.greaterThan(root.get("date"), LocalDate.now())).thenReturn(mock(Predicate.class));
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mockItems);

        // Call repository method
        List<Item> result = customItemRepository.findItemsShouldHaveFutureExpiryDate();

        // Verify
        assertEquals(mockItems.size(), result.size());
        verify(entityManager, times(1)).createQuery(criteriaQuery);
    }

    @Test
    public void testFindTopItemsByPrice() {
        // Mock data
        int limit = 10;
        List<Item> mockItems = Arrays.asList(new Item(), new Item());


        // Mock EntityManager methods
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Item.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Item.class)).thenReturn(root);
        when(criteriaBuilder.asc(root.get("price"))).thenReturn(mock(Order.class));
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.setMaxResults(limit)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mockItems);

        // Call repository method
        List<Item> result = customItemRepository.findTopItemsByPrice(limit);

        // Verify
        assertEquals(mockItems.size(), result.size());
        verify(entityManager, times(1)).createQuery(criteriaQuery);
        verify(typedQuery, times(1)).setMaxResults(limit);
    }

   /* @Test
    public void testFindNoOfItemsByName() {
        // Mock data
        List<Object[]> mockItemsArrays = Arrays.asList(new Object[]{"Item1", 3}, new Object[]{"Item2", 5});

        // Mock EntityManager methods
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Object[].class)).thenReturn(criteriaObjectArrayQuery);
        when(criteriaQuery.from(Item.class)).thenReturn(root);
        when(criteriaBuilder.count(root.get("name"))).thenReturn(path);
        when(criteriaQuery.multiselect(root.get("name"), criteriaBuilder.count(root.get("name")))).thenReturn(criteriaQuery);
        when(criteriaQuery.groupBy(root.get("name"))).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mockItemsArrays);

        // Call repository method
        List<Object[]> result = customItemRepository.findNoOfItemsByName();

        // Verify
        assertEquals(mockItemsArrays.size(), result.size());
        verify(entityManager, times(1)).createQuery(criteriaQuery);
    }*/

}

