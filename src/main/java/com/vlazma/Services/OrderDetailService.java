package com.vlazma.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.vlazma.Dto.ResponseData;
import com.vlazma.Dto.OrderDetail.OrderDetailRequest;
import com.vlazma.Dto.OrderDetail.OrderDetailResponse;
import com.vlazma.Models.OrderDetail;
import com.vlazma.Repositories.ChartRepository;
import com.vlazma.Repositories.OrderDetailRepository;
import com.vlazma.Repositories.OrderRepository;
import java.util.Collections;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ChartRepository chartRepository;
    @Autowired
    private OrdersService ordersService;

    public ResponseEntity<ResponseData<OrderDetailResponse>> create(OrderDetailRequest orderDetailRequest,
            Errors errors) {
        var order = orderRepository.findById(0);
        var chart = chartRepository.findById(0);
        try {
            order = orderRepository.findById(Integer.parseInt(orderDetailRequest.getOrderId()));
            chart = chartRepository.findById(Integer.parseInt(orderDetailRequest.getChartId()));
        } catch (NumberFormatException e) {
        }
        ResponseData<OrderDetailResponse> responseData = new ResponseData<>();
        if (errors.hasErrors() || order.isEmpty() || chart.isEmpty()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(order.isEmpty() ? "Order Not Foubd" : null);
            responseData.getMessages().add(chart.isEmpty() ? "Chart Not Found" : null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);

        }
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setChart(chart.get());
        orderDetail.setOrder(order.get());
        orderDetail.setOrderTotal(chart.get().getGrandTotal());
        orderDetail.setShipCost(Integer.parseInt(orderDetailRequest.getShipCost()));
        orderDetailRepository.save(orderDetail);
        ordersService.changeTotalPrice(orderDetail);
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(OrderDetailResponse
                .builder()
                .orderId(orderDetail.getOrder().getId())
                .chartId(orderDetail.getChart().getId())
                .orderTotal(orderDetail.getOrderTotal())
                .shipCost(orderDetail.getShipCost())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);

    }
}
