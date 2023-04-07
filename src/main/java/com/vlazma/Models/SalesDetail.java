package com.vlazma.Models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.AssociationOverride;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Transient;
@Entity
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.sales",joinColumns = @JoinColumn(name = "sales_id")),
    @AssociationOverride(name = "primaryKey.orders",joinColumns = @JoinColumn(name = "order_id"))
})
public class SalesDetail {
    @EmbeddedId
    private SalesDetailId primaryKey = new SalesDetailId();
    @Transient
    public Sales getSales(){
        return getPrimaryKey().getSales();
    }

    public void setSales(Sales sales) {
        getPrimaryKey().setSales(sales);
    }

    @Transient
    public Orders getOrders(){
        return getPrimaryKey().getOrders();
    }

    public void setOrder(Orders orders){
        getPrimaryKey().setOrders(orders);
    }
    
}
