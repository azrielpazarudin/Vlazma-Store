package com.vlazma.Models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailId {
    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders order;
    @OneToOne
    @JoinColumn(name="chart_id")
    private Chart chart;
}
