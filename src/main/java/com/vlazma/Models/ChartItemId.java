package com.vlazma.Models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ChartItemId {
    @OneToOne
    private Chart chart;
    @OneToOne
    private Product product;
}
