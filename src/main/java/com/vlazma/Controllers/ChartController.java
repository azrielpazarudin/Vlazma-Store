package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Dto.Chart.ChartRequest;
import com.vlazma.Services.ChartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/chart")
public class ChartController {
    @Autowired
    private ChartService chartService;

    @GetMapping("/")
    public Object getAll() {
        return chartService.getAllCharts();
    }

    @PostMapping("/")
    public Object create(@Valid @RequestBody ChartRequest chartRequest, Errors errors) {
        return chartService.create(chartRequest, errors);
    }

    @GetMapping("/check-out/{id}")
    public Object checkOut(@PathVariable int id){
        return chartService.checkOutChart(id);
    }
}
