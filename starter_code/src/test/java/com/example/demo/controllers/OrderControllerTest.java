package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.demo.controllers.ControllerTestUtils.createUserForTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    public static final String USERNAME = "demoUser";
    private OrderController orderController;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        com.example.demo.TestUtils.injectObjects(orderController, "userRepository", userRepository);
        com.example.demo.TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void testSubmit() {
        User user = createUserForTest();
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        ResponseEntity<UserOrder> response =  orderController.submit(USERNAME);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testSubmitNullUser() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(null);

        ResponseEntity<UserOrder> response =  orderController.submit(USERNAME);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersForUser() {
        User user = createUserForTest();
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);

        orderController.submit(USERNAME);

        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser(USERNAME);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testGetOrdersForUserNullUser() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(null);

        orderController.submit(USERNAME);

        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser(USERNAME);

        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

}