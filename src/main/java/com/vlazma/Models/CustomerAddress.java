package com.vlazma.Models;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.customers",joinColumns = @JoinColumn(name = "CUSTOMER_ID")),
    @AssociationOverride(name = "primaryKey.address",joinColumns = @JoinColumn(name = "ADDRESS_ID"))
})
public class CustomerAddress {
    @EmbeddedId
    private CustomerAddressId primaryKey = new CustomerAddressId();
    @Transient
    public Customers getCustomer(){
        return getPrimaryKey().getCustomers();
    }

    public void setCustmer(Customers customers) {
        getPrimaryKey().setCustomers(customers);
    }

    @Transient
    public Address getAddress(){
        return getPrimaryKey().getAddress();
    }

    public void setAddress(Address address){
        getPrimaryKey().setAddress(address);
    }
}
