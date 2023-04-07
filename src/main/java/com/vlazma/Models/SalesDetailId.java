package com.vlazma.Models;

import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesDetailId {
    @OneToOne
    private Sales sales;
    @OneToOne
    private Orders orders;
}
