package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Dto.Customers.CustomersRequest;
import com.vlazma.Services.CustomersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/customer")
public class CustomerController {
    @Autowired
    private CustomersService customersService;

    @GetMapping("/")
    public Object get() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Hanya Dapat Dilakukan Admin");
    }

    @GetMapping("/find-by-id/{id}")
    public Object getById(@PathVariable int id) {
        return customersService.findById(id);
    }

    @PostMapping("/create/")
    public Object create(@Valid @RequestBody CustomersRequest customersRequest, Errors errors) {
        return customersService.createCustomers(customersRequest, errors);
    }

    @PostMapping("/edit/{id}")
    public Object edit(@PathVariable int id, @Valid @RequestBody CustomersRequest customersRequest, Errors errors) {
        return customersService.editCustomers(customersRequest, id, errors);
    }

    @DeleteMapping("/deactivate/{id}")
    public Object delete(@PathVariable int id) {
        return customersService.deactivateCustomer(id);
    }
}
