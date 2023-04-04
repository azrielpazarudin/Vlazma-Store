package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.ChartItem;
import com.vlazma.Models.ChartItemId;

public interface ChartItemRepository extends JpaRepository<ChartItem, ChartItemId> {
}
