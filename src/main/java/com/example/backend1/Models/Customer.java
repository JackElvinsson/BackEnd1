package com.example.backend1.Models;

import jakarta.persistence.*;
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

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String ssn;
/*
    public Customer(String name, String ssn) {
        this.name = name;
        this.ssn = ssn;
    }

 */

    @OneToMany
    @JoinTable
    private List<Order> orderList = new ArrayList<>();

}
