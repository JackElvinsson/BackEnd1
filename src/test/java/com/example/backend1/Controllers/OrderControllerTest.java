package com.example.backend1.Controllers;

import com.example.backend1.Models.Customer;
import com.example.backend1.Models.Order;
import com.example.backend1.Repositories.CustomerRepo;
import com.example.backend1.Repositories.OrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepo orderRepository;

    @MockBean
    private CustomerRepo customerRepository;

    @BeforeEach
    public void init() {
        Customer c1 = new Customer(1L, "Test Testsson", "900110", null);
        Customer c2 = new Customer(2L, "Test Testberg", "800615", null);
        Customer c3 = new Customer(3L, "Test Testman", "701013", null);

        Order o1 = new Order(4L, "01-01-01", c1, null);
        Order o2 = new Order(5L, "02-02-02", c2, null);
        Order o3 = new Order(6L, "03-03-03", c3, null);

        when(orderRepository.findById(4L)).thenReturn(Optional.of(o1));
        when(orderRepository.findById(5L)).thenReturn(Optional.of(o2));
        when(orderRepository.findById(6L)).thenReturn(Optional.of(o3));
        when(orderRepository.findAll()).thenReturn(Arrays.asList(o1, o2, o3));

        when(orderRepository.findByCustomerId(1L)).thenReturn(List.of(o1));
        when(orderRepository.findByCustomerId(2L)).thenReturn(List.of(o2));
        when(orderRepository.findByCustomerId(3L)).thenReturn(List.of(o3));
    }

    @Test
    public void getAllOrdersTest() throws Exception {
        this.mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[1].id").value(5))
                .andExpect(jsonPath("$[2].id").value(6));
    }

    @Test
    public void findOrderByCustomerIdTest() throws Exception {
        // .param("customerId", "1") is the same as /orders?customerId=1
        this.mockMvc.perform(get("/orders").param("customerId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4));
    }

}
