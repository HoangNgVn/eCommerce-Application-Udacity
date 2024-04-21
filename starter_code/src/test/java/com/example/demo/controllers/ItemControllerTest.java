package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.controllers.ControllerTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;
    private final ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void testGetItems() {
        Item item1 = getItem_1();
        Item item2 = getItem_2();
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        when(itemRepository.findAll()).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        List<Item> retrievedItems = response.getBody();
        assertNotNull(retrievedItems);
        assertEquals(2, retrievedItems.size());
        assertEquals(item1, retrievedItems.get(0));
        assertEquals(item2, retrievedItems.get(1));
    }

    @Test
    public void testGetItemById() {
        Item item1 = getItem_1();
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item1));

        ResponseEntity<Item> response = itemController.getItemById(1L);

        assertEquals(200, response.getStatusCodeValue());
        Item retrievedItem = response.getBody();
        assertEquals(item1, retrievedItem);
        assertNotNull(retrievedItem);
    }

    @Test
    public void testGetItemsByName() {
        Item item1 = getItem_1();
        List<Item> items = new ArrayList<>();
        items.add(item1);
        when(itemRepository.findByName("Round Widget")).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Round Widget");

        assertEquals(200, response.getStatusCodeValue());
        List<Item> retrievedItems = response.getBody();
        assertNotNull(retrievedItems);
        assertEquals(item1, retrievedItems.get(0));
    }
}