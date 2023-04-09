package com.vlazma.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Enumerations.OrderStatus;
import com.vlazma.Models.Orders;


public interface OrderRepository extends JpaRepository<Orders,Integer>{
    List<Orders> findByCustomersIdAndOrderStatus(int customerId,OrderStatus orderStatus);
    List<Orders> findByCustomersId(int id);
}
