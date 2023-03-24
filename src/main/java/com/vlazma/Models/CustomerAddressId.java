package com.vlazma.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressId {
    @ManyToOne
    (cascade = CascadeType.ALL)
    private Customers customers;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
}
