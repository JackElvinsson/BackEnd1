package com.example.backend1.Models;

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
@Table(name = "purchase_order")
public class Order {
    public Order(String date, Customer customer) {
        this.date = date;
        this.customer = customer;
    }

    public Order(String date, Customer customer, List<Item> itemList) {
        this.date = date;
        this.customer = customer;
        this.itemList = itemList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String date;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "order_item",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> itemList = new ArrayList<>();
}
