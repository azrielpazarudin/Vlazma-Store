package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Dto.Orders.OrdersRequest;
import com.vlazma.Services.OrdersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @GetMapping("/")
    public Object get(){
        return ordersService.getAllOrders();
    }

    @GetMapping("/current-order/{id}")
    private Object currentOrder(@PathVariable int id){
        return ordersService.currentOrder(id);
    }

    @GetMapping("/history-order/{id}")
    private Object historyOrder(@PathVariable int id){
        return ordersService.historyOrder(id);
    }

    @PostMapping("change-order-status/{id}")
    public void cOS(@PathVariable int id,@RequestBody String status){
         ordersService.changeStatusOrder(id, status);
    }
}
