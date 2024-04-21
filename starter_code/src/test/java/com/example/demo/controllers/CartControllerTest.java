package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.example.demo.controllers.ControllerTestUtils.createUserForTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    public static final String USERNAME = "demoUser";
    private CartController cartController;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final CartRepository cartRepository = mock(CartRepository.class);
    private final ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void addTocart() {
        User user = createUserForTest();
        Item item = ControllerTestUtils.getItem_1();

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("demoUser");
        request.setItemId(1L);
        request.setQuantity(1);

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertEquals(200, response.getStatusCodeValue());
        Cart retrievedCart = response.getBody();
        assert retrievedCart != null;
        assertEquals(1L, (Object) retrievedCart.getId());
        List<Item> items = retrievedCart.getItems();
        assertEquals(2, items.size());
        assertEquals(new BigDecimal("5.98"), retrievedCart.getTotal());
        assertEquals(user, retrievedCart.getUser());
    }

    @Test
    public void testAddToCartNullUser() {
        Item item = ControllerTestUtils.getItem_1();

        when(userRepository.findByUsername(USERNAME)).thenReturn(null);
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("demoUser");
        request.setItemId(1L);
        request.setQuantity(1);

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void removeFromcart() {
        User user = createUserForTest();
        Item item = ControllerTestUtils.getItem_1();

        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("demoUser");
        request.setItemId(1L);
        request.setQuantity(1);

        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        Cart retrievedCart = response.getBody();
        assert retrievedCart != null;
        assertEquals(1L, (Object) retrievedCart.getId());
        List<Item> items = retrievedCart.getItems();
        assertNotNull(items);
        assertEquals(0, items.size());
        assertEquals(new BigDecimal("0.00"), retrievedCart.getTotal());
    }

}