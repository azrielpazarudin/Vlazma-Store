package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.OrderDetail;
import com.vlazma.Models.OrderDetailId;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,OrderDetailId> {
    
}
