package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Services.OrdersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/vlazma/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @Operation(summary = "Showing Order Data", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/")
    public Object get(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return ordersService.getAllOrders();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin Can Acces It");
    }

    @Operation(summary = "Showing Customer Current Order Data", security = {
            @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/current-order")
    private Object currentOrder(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_CUSTOMER")) {
            return ordersService.currentOrder(request);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Customer Can Acces It");
    }

    @Operation(summary = "Showing Customer History Order Data", security = {
            @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/history-order")
    private Object historyOrder(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_CUSTOMER")) {
            return ordersService.historyOrder(request);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Customer Can Acces It");

    }

    @Operation(summary = "Change Order Data", security = {
            @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/change-order-status/{id}")
    public Object cOS(HttpServletRequest request, @PathVariable int id, @RequestBody String status) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            ordersService.changeStatusOrder(id, status);
            return ResponseEntity.ok("Status Order Changed");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin Can Acces It");
    }
}
