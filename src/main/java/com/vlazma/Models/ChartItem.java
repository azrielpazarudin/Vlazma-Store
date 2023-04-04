package com.vlazma.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.chart",joinColumns = @JoinColumn(name = "chart_id")),
    @AssociationOverride(name = "primaryKey.product",joinColumns = @JoinColumn(name = "product_id"))
})
public class ChartItem {
    @EmbeddedId
    private ChartItemId primaryKey = new ChartItemId();
    @Transient
    public Chart getChart(){
        return getPrimaryKey().getChart();
    }

    public void setChart(Chart chart) {
        getPrimaryKey().setChart(chart);
    }

    @Transient
    public Product getProduct(){
        return getPrimaryKey().getProduct();
    }

    public void setProduct(Product product){
        getPrimaryKey().setProduct(product);;
    }

    private int quantity;
}
