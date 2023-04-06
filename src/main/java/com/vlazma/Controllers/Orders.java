package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Dto.Orders.OrdersRequest;
import com.vlazma.Services.OrdersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/order")
public class Orders {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/")
    public Object create(@Valid@RequestBody OrdersRequest ordersRequest,Errors errors){
        return ordersService.create(ordersRequest, errors);
    }
}
