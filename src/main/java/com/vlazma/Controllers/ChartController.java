package com.vlazma.Controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Services.ChartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/vlazma/chart")
public class ChartController {
    @Autowired
    private ChartService chartService;

    @GetMapping("/")
    public Object getAll() {
        return chartService.getAllCharts();
    }

    @Operation(summary = "Get Customer Current Chart Data", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/current-chart")
    public Object currentChart(HttpServletRequest request, @PathVariable int id) {
        if (request.isUserInRole("ROLE_CUSTOMER")) {
            return chartService.currentChart(request);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This request is for Customer");
    }

    @Operation(summary = "Get Customer History Chart Data", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/history-chart")
    public Object historyChart(HttpServletRequest request, @PathVariable int id) {
        if (request.isUserInRole("ROLE_CUSTOMER")) {
            return chartService.historyChart(request);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This request is for Customer");
    }

    @Operation(summary = "Adding Chart Data", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/")
    public Object create(HttpServletRequest request) {
        if(request.isUserInRole("ROLE_CUSTOMER")){
        return chartService.create(request);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This request is for Customer");
    }

    @Operation(summary = "Check Out The Chart", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/check-out/{id}")
    public Object checkOut(HttpServletRequest request,@PathVariable int id) throws IOException {
        if(request.isUserInRole("ROLE_CUSTOMER")){
        return chartService.checkOutChart(id);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This request is for Cusomer");
    }
}
