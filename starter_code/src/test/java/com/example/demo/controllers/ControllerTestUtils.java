package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ControllerTestUtils {

    public static Item getItem_1() {
        Item item = new Item();
        item.setId(0L);
        item.setName("Round Widget");
        item.setPrice(new BigDecimal("2.99"));
        item.setDescription("A widget that is round");
        return item;
    }

    public static Item getItem_2() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Square Widget");
        item.setPrice(new BigDecimal("1.99"));
        item.setDescription("A widget that is square");
        return item;
    }

    public static User createUserForTest() {
        User user = new User();
        user.setUsername("demoUser");

        Item item = ControllerTestUtils.getItem_1();

        Cart cart = new Cart();
        cart.setId(1L);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);
        cart.setUser(user);
        cart.setTotal(new BigDecimal("2.99"));
        user.setCart(cart);
        return user;
    }
}
