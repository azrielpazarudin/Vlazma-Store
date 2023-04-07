package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Chart;
import java.util.List;
public interface ChartRepository extends JpaRepository<Chart,Integer>{
    List<Chart> findByCustomerId(int id);
}
