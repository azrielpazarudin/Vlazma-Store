package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Dto.OrderDetail.OrderDetailRequest;
import com.vlazma.Services.OrderDetailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/order-detail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/")
    public Object create(@Valid@RequestBody OrderDetailRequest orderDetailRequest,Errors errors){
        return orderDetailService.create(orderDetailRequest, errors);
    }
}
