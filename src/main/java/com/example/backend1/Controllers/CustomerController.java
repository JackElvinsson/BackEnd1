package com.example.backend1.Controllers;

import com.example.backend1.Models.Customer;
import com.example.backend1.Repositories.CustomerRepo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerRepo customerRepo;


    CustomerController(CustomerRepo customerRepo){
        this.customerRepo = customerRepo;

    }

    @RequestMapping("customers")
    public List<Customer> getAllCustomters(){
        return customerRepo.findAll();
    }

    @RequestMapping("customers/{id}")
    public Customer findCustomerById(@PathVariable Long id){
        return customerRepo.findById(id).orElse(null);
    }
}
