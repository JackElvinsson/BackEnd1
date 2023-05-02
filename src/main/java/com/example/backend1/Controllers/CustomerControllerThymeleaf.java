package com.example.backend1.Controllers;

import com.example.backend1.Models.Customer;
import com.example.backend1.Repositories.CustomerRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerControllerThymeleaf {

    private final CustomerRepo repo;

    CustomerControllerThymeleaf(CustomerRepo customerRepo) {
        this.repo = customerRepo;
    }

    @RequestMapping("/allcustomers")
    public String getAll(Model model) {
        List<Customer> customers = repo.findAll();
        model.addAttribute("allCustomers", customers);
        model.addAttribute("customerName", "Name");
        model.addAttribute("customerSsn", "Ssn");
        return "getCustomers";
    }

    @RequestMapping("/customers/addbyform")
    public String showAddCustomerForm(Model model) {
        return "addCustomer";
    }

    @PostMapping("/customers/addByFormReceival")
    public String addCustomerByForm(@RequestParam String customerName,
                                    @RequestParam String SSN, Model model) {

        if (customerName.isEmpty() || SSN.isEmpty()) {

            model.addAttribute("errorMessage", "Invalid input. Namn eller SSN saknas.");

            return "addCustomer";
        }
        Customer newCustomer = new Customer();
        newCustomer.setName(customerName);
        newCustomer.setSsn(SSN);
        repo.save(newCustomer);

        return "redirect:/allcustomers";
    }


}
