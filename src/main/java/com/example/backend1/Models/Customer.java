package com.example.backend1.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    public Customer(String name, List<Order> orderList) {
        this.name = name;
        this.orderList = orderList;
    }

    public Customer(String name, String ssn) {
        this.name = name;
        this.ssn = ssn;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String ssn;

    @OneToMany(mappedBy = "customer")
    private List<Order> orderList = new ArrayList<>();
}
