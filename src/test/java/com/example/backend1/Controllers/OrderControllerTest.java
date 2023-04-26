package com.example.backend1.Controllers;

import com.example.backend1.Models.Order;
import com.example.backend1.Repositories.CustomerRepo;
import com.example.backend1.Repositories.OrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepo orderRepository;

    @MockBean
    private CustomerRepo customerRepository;

    @BeforeEach
    public void setup() {
        // Set up mock data for orderRepository
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setId(1L);
        orders.add(order1);
        Order order2 = new Order();
        order2.setId(2L);
        orders.add(order2);
        when(orderRepository.findByCustomerId(1L)).thenReturn(orders);
    }

    @Test
    public void testGetOrdersByCustomerId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders?customerId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));
    }
}