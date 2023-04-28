package com.example.backend1.Controllers;

import com.example.backend1.Models.Customer;
import com.example.backend1.Repositories.CustomerRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CustomerControllerThymeleaf {

    private final CustomerRepo repo;

    CustomerControllerThymeleaf(CustomerRepo customerRepo){
        this.repo = customerRepo;
    }

    @RequestMapping("/allcustomers")
    public String getAll(Model model){
        List<Customer> customers = repo.findAll();
        model.addAttribute("allCustomers", customers);
        model.addAttribute("customerName", "Name");
        model.addAttribute("customerSsn", "Ssn");
        return "getCustomers";
    }
}
