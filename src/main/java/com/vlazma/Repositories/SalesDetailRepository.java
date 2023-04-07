package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.SalesDetail;
import com.vlazma.Models.SalesDetailId;

public interface SalesDetailRepository extends JpaRepository<SalesDetail,SalesDetailId>{
    
}