package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Dto.ChartItem.ChartItemRequest;
import com.vlazma.Services.ChartItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/chart-item")
public class ChartItemController {
    @Autowired
    private ChartItemService chartItemService;

    @GetMapping("/")
    public Object get(){
        return chartItemService.getAllChartItems();
    }
    @PostMapping("/")
    public Object create(@Valid@RequestBody ChartItemRequest chartItemRequest,Errors errors){
        return chartItemService.create(chartItemRequest, errors);
    }
    @PostMapping("edit-chart-item/{id}/{product}")
    public Object editCurrentCart(@PathVariable int id,@PathVariable int product,@RequestBody int newQuantity){
        return chartItemService.editCurrentChartProduct(id, product, newQuantity);
    }
}
