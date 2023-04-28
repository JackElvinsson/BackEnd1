package com.example.backend1;

import com.example.backend1.Models.Customer;
import com.example.backend1.Models.Item;
import com.example.backend1.Models.Order;
import com.example.backend1.Repositories.CustomerRepo;
import com.example.backend1.Repositories.ItemRepo;
import com.example.backend1.Repositories.OrderRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackEnd1Application {

    public static void main(String[] args) {
        SpringApplication.run(BackEnd1Application.class, args);
    }

    @Bean
    public CommandLineRunner clr(ItemRepo itemRepo, CustomerRepo customerRepo, OrderRepo orderRepo) {
        return (args) -> {

            Customer c1 = new Customer("Emma", "123456");
            Customer c2 = new Customer("Jack", "345676");
            Customer c3 = new Customer("Thomas", "456767");
            Customer c4 = new Customer("Rasmus", "5678896");

            customerRepo.save(c1); customerRepo.save(c2); customerRepo.save(c3); customerRepo.save(c4);

            Item i1 = new Item("Shoe", 1500);
            Item i2 = new Item("Book", 150);
            Item i3 = new Item("Time", 2390294);
            Item i4 = new Item("Snus", 30);

            itemRepo.save(i1); itemRepo.save(i2); itemRepo.save(i3); itemRepo.save(i4);

            Order o1 = new Order(c1);
            Order o2 = new Order(c2);
            Order o3 = new Order(c3);
            Order o4 = new Order(c4);

            o1.addItem(i1); o1.addItem(i2);
            o2.addItem(i2); o2.addItem(i3);
            o3.addItem(i3); o3.addItem(i4);
            o4.addItem(i4); o3.addItem(i1);

            orderRepo.save(o1); orderRepo.save(o2); orderRepo.save(o3); orderRepo.save(o4);


        };
    }

}
