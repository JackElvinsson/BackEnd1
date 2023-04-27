package com.example.backend1.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "order_item",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> itemList = new ArrayList<>();

    public Order(Customer customer) {
        this.customer = customer;
        this.date = LocalDate.now();
    }

    public Order(long id, Customer customer, LocalDate date) {
        this.id=id;
        this.customer = customer;
        this.date = date;
    }

    public Order(Customer customer, List<Item> itemList) {
        this.customer = customer;
        this.itemList = itemList;
        this.date = LocalDate.now();
    }

    public void addItem(Item item) {
        itemList.add(item);
    }
}
