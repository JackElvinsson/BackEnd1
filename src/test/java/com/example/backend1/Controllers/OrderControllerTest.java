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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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


    @BeforeEach
    public void init() {
        Customer c1 = new Customer(1L, "Test Testsson", "900110", null);
        Customer c2 = new Customer(2L, "Test Testberg", "800615", null);
        Customer c3 = new Customer(3L, "Test Testman", "701013", null);

        Order o1 = new Order(4L, c1, LocalDate.of(2020, 1,1));
        Order o2 = new Order(5L, c2, LocalDate.of(2021, 2,2));
        Order o3 = new Order(6L, c3, LocalDate.of(2022, 3,3));

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
                .andExpect(content().json("[{\"id\":4,\"date\":\"2020-01-01\",\"itemList\":[]},{\"id\":5,\"date\":\"2021-02-02\",\"itemList\":[]},{\"id\":6,\"date\":\"2022-03-03\",\"itemList\":[]}]"));
    }


    @Test
    public void findOrderByCustomerIdTest() throws Exception {
        // .param("customerId", "1") is the same as /orders?customerId=1
        this.mockMvc.perform(get("/orders").param("customerId", "1"))

                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":4,\"date\":\"2020-01-01\",\"itemList\":[]}]"));

    }


}
