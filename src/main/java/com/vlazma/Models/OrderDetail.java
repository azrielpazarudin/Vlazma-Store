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
@Data
@AllArgsConstructor
@NoArgsConstructor
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.order",joinColumns = @JoinColumn(name = "chart_id")),
    @AssociationOverride(name = "primaryKey.chart",joinColumns = @JoinColumn(name = "product_id"))
})
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId primaryKey = new OrderDetailId();
    @Transient
    public Chart getChart(){
        return getPrimaryKey().getChart();
    }

    public void setChart(Chart chart) {
        getPrimaryKey().setChart(chart);
    }

    @Transient
    public Orders getOrder(){
        return getPrimaryKey().getOrder();
    }

    public void setOrder(Orders order){
        getPrimaryKey().setOrder(order);;
    }
}
