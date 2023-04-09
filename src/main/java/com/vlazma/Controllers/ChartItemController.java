package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Dto.ChartItem.ChartItemRequest;
import com.vlazma.Services.ChartItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
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

    @Operation(summary = "Showing Current Chart Item", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/current-chart-item/{id}")
    public Object currentChartItem(HttpServletRequest request,@PathVariable int id){
        if(request.isUserInRole("ROLE_CUSTOMER")){
        return chartItemService.currentChartItem(request);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This request is for Cusomer");
    }

    @Operation(summary = "Adding Product To Chart / Adding Chart Item", security = { @SecurityRequirement(name = "bearer-key") })   
    @PostMapping("/")
    public Object create(HttpServletRequest request,@Valid@RequestBody ChartItemRequest chartItemRequest,Errors errors){
        if(request.isUserInRole("ROLE_CUSTOMER")){
        return chartItemService.create(chartItemRequest, errors);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This request is for Cusomer");

    }

    @Operation(summary = "Edit Chart Item", security = { @SecurityRequirement(name = "bearer-key") })   
    @PostMapping("edit-chart-item/{id}/{product}")
    public Object editCurrentCart(HttpServletRequest request,@PathVariable int id,@PathVariable int product,@RequestBody int newQuantity){
        if(request.isUserInRole("ROLE_CUSTOMER")){
        return chartItemService.editCurrentChartProduct(id, product, newQuantity);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This request is for Cusomer");


    }
}
