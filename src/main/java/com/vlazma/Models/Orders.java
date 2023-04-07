package com.vlazma.Models;

import java.time.LocalDateTime;

import com.vlazma.Enumerations.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name="customer_id")
    private Customers customers;
    private LocalDateTime orderDate;
    private String origin;
    private String destination;
    private String courierName;
    private int total_price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
