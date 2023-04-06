package com.vlazma.Services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.vlazma.Dto.ResponseData;
import com.vlazma.Dto.Orders.OrdersRequest;
import com.vlazma.Dto.Orders.OrdersResponse;
import com.vlazma.Models.Address;
import com.vlazma.Models.OrderDetail;
import com.vlazma.Models.Orders;
import com.vlazma.Repositories.CustomersAddressRepository;
import com.vlazma.Repositories.CustomersRepository;
import com.vlazma.Repositories.OrderRepository;

@Service

public class OrdersService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private CustomersAddressRepository customersAddressRepository;

    public ResponseEntity<ResponseData<OrdersResponse>> create(OrdersRequest ordersRequest, Errors errors) {
        ResponseData<OrdersResponse> responseData = new ResponseData<>();
        var customer = customersRepository.findById(Integer.parseInt(ordersRequest.getCustomerId()));
        if (errors.hasErrors()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        var custAddresses = customersAddressRepository.findAll();
        var custDestination = new Address();
        for (var x : custAddresses) {
            if (x.getCustomer().getId() == customer.get().getId()) {
                custDestination.setCity(x.getAddress().getCity());
                break;
            }
        }

        var order = Orders.builder()
                .customers(customer.get())
                .orderDate(LocalDateTime.now())
                .origin("23|Kota Bandung")
                .destination(custDestination.getCity())
                .courierName("JNE")
                .total_price(0).build();
        orderRepository.save(order);
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(OrdersResponse.builder()
                .id(order.getId())
                .customerName(customer.get().getFullName())
                .orderDate(order.getOrderDate())
                .origin(order.getOrigin())
                .destination(order.getDestination())
                .courierName(order.getCourierName())
                .total_price(0)
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    public void changeTotalPrice(OrderDetail orderDetail) {
        var order = orderRepository.findById(orderDetail.getOrder().getId());
        var updateOrder = order.get();
        updateOrder.setTotal_price(orderDetail.getChart().getGrandTotal() + orderDetail.getShipCost());
        orderRepository.save(updateOrder);
    }
    
}
