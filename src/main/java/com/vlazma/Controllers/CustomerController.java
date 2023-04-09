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
import com.vlazma.Dto.Users.ChangePassword;
import com.vlazma.Services.CustomersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/customer")
public class CustomerController {
    @Autowired
    private CustomersService customersService;

    @Operation(summary = "Show Customer Data", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/")
    public Object get(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            customersService.getAllCustomers();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Hanya Dapat Dilakukan Admin");
    }

    @Operation(summary = "Show Customer Data", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/find-by-id/{id}")
    public Object getById(HttpServletRequest request,@PathVariable int id) {
        if(request.isUserInRole("ROLE_ADMIN")){
        return customersService.findById(id);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin Can Acces It");

    }

    @Operation(summary = "Create Customer Data", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/create/")
    public Object create(HttpServletRequest request, @Valid @RequestBody CustomersRequest customersRequest,
            Errors errors) {
        if (request.isUserInRole("ROLE_CUSTOMER")) {
            return customersService.createCustomers(customersRequest, errors);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Customer Can Acces It");
    }

    @Operation(summary = "Edit Customer Data", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/edit/{id}")
    public Object edit(HttpServletRequest request, @PathVariable int id,
            @Valid @RequestBody CustomersRequest customersRequest, Errors errors) {
        if (request.isUserInRole("ROLE_CUSTOMER")) {
            return customersService.editCustomers(customersRequest, id, errors);

        }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Customer Can Acces It");
    }

    @Operation(summary = "Edit Customer Data", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/change-password/{id}")
    public Object changePassword(HttpServletRequest request,@PathVariable int id, @Valid @RequestBody ChangePassword changePassword,
            Errors errors) {
        if(request.isUserInRole("ROLE_CUSTOMER")){
        return customersService.changePassword(id, changePassword, errors);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Customer Can Acces It");
    }

    @DeleteMapping("/deactivate/{id}")
    public Object delete(@PathVariable int id) {
        return customersService.deactivateCustomer(id);
    }
}
