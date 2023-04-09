package com.vlazma.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vlazma.Models.OrderDetail;
import com.vlazma.Repositories.ChartRepository;
import com.vlazma.Repositories.OrderDetailRepository;
import com.vlazma.Repositories.OrderRepository;

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

    public int create(int orderId, int chartId, int shipCost) {
        var order = orderRepository.findById(orderId);
        var chart = chartRepository.findById(chartId);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setChart(chart.get());
        orderDetail.setOrder(order.get());
        orderDetail.setOrderTotal(chart.get().getGrandTotal());
        orderDetail.setShipCost(shipCost);
        orderDetailRepository.save(orderDetail);
        return ordersService.changeTotalPrice(orderDetail);
    }
}
