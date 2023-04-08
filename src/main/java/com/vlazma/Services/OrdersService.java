package com.vlazma.Services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.vlazma.Dto.ResponseData;
import com.vlazma.Dto.Orders.OrdersResponse;
import com.vlazma.Enumerations.OrderStatus;
import com.vlazma.Models.Address;
import com.vlazma.Models.OrderDetail;
import com.vlazma.Models.Orders;
import com.vlazma.Models.SalesDetail;
import com.vlazma.Models.Sales;
import com.vlazma.Repositories.CustomersAddressRepository;
import com.vlazma.Repositories.CustomersRepository;
import com.vlazma.Repositories.OrderRepository;
import com.vlazma.Repositories.SalesRepository;
import com.vlazma.Utils.ROBody;
import com.vlazma.Utils.RORequest;

import jakarta.persistence.criteria.Order;

import com.vlazma.Repositories.SalesDetailRepository;

@Service

public class OrdersService {
    @Autowired
    private RORequest roRequest;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private CustomersAddressRepository customersAddressRepository;
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private SalesDetailRepository salesDetailRepository;

    public ResponseEntity<ResponseData<OrdersResponse>> create(int customerId, int chartId) throws IOException {
        ResponseData<OrdersResponse> responseData = new ResponseData<>();
        var customer = customersRepository.findById(customerId);
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
                .total_price(0)
                .orderStatus(OrderStatus.PROCESSED)
                .build();

        orderRepository.save(order);
        String[] originSplit = order.getOrigin().split("|");
        String[] destSplit = order.getDestination().split("|");
        int shipCost = roRequest.countShipmentCost(ROBody.builder()
                .origin(originSplit[0]+originSplit[1])
                .destination(destSplit[0]+destSplit[1])
                .weight(500)
                .courier(order.getCourierName().toLowerCase())
                .build());

        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(OrdersResponse.builder()
                .id(order.getId())
                .customerName(customer.get().getFullName())
                .orderDate(order.getOrderDate())
                .origin(order.getOrigin())
                .destination(order.getDestination())
                .courierName(order.getCourierName())
                .total_price(orderDetailService.create(order.getId(), chartId, shipCost))
                .orderStatus(order.getOrderStatus().toString())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    public ResponseEntity<ResponseData<List<OrdersResponse>>> getAllOrders(){
        List<Orders> orders = orderRepository.findAll();
        ResponseData<List<OrdersResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(orders.stream().map(this::mapToResponse).toList());
        return ResponseEntity.ok(responseData);
    }

    private OrdersResponse mapToResponse (Orders orders){
        return OrdersResponse.builder()
        .id(orders.getId())
        .customerName(orders.getCustomers().getFullName())
        .orderDate(orders.getOrderDate())
        .origin(orders.getOrigin())
        .destination(orders.getDestination())
        .courierName(orders.getCourierName())
        .total_price(orders.getTotal_price())
        .orderStatus(orders.getOrderStatus().toString())
        .build();
                
    }
    
    public int changeTotalPrice(OrderDetail orderDetail) {
        var order = orderRepository.findById(orderDetail.getOrder().getId());
        var updateOrder = order.get();
        updateOrder.setTotal_price(orderDetail.getChart().getGrandTotal() + orderDetail.getShipCost());
        orderRepository.save(updateOrder);
        return updateOrder.getTotal_price();
    }

    public ResponseEntity<ResponseData<Object>> currentOrder(int customerId) {
        List<Orders> orders = new ArrayList<>();
        for (var x : orderRepository.findByCustomersId(customerId)) {
            for (var y : orderRepository.findByCustomersIdAndOrderStatus(customerId, OrderStatus.DELIVERED)) {
                if (x.getOrderStatus().equals(y.getOrderStatus())) {
                    orders.add(x);
                }
            }
        }
        for (var x : orderRepository.findByCustomersId(customerId)) {
            for (var y : orderRepository.findByCustomersIdAndOrderStatus(customerId, OrderStatus.PROCESSED)) {
                if (x.getOrderStatus().equals(y.getOrderStatus())) {
                    orders.add(x);
                }
            }
        }
        ResponseData<Object> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(orders.stream().map(this::mapToResponse).toList());
        return ResponseEntity.ok(responseData);

    }

    public ResponseEntity<ResponseData<Object>> historyOrder(int customerId){
        ResponseData<Object> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(orderRepository.findByCustomersIdAndOrderStatus(customerId,OrderStatus.RECIEVED).stream().map(this::mapToResponse).toList());
        return ResponseEntity.ok(responseData);
    }
    
    public void changeStatusOrder(int id, String Status) {
        var order = orderRepository.findById(id);
        order.get().setOrderStatus(OrderStatus.RECIEVED);
        orderRepository.save(order.get());
        if (order.get().getOrderStatus().equals(OrderStatus.RECIEVED)) {
            Sales sales = Sales
                    .builder()
                    .date(LocalDateTime.now())
                    .total(0)
                    .build();
            salesRepository.save(sales);
            SalesDetail salesDetail = new SalesDetail();
            salesDetail.setOrder(order.get());
            salesDetail.setSales(sales);
            salesDetailRepository.save(salesDetail);
            var updatedSales = salesRepository.findById(salesDetail.getSales().getId());
            updatedSales.get().setTotal(salesDetail.getOrders().getTotal_price());
            salesRepository.save(updatedSales.get());
        }
    }
}
