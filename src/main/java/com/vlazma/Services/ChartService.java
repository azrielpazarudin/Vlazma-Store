package com.vlazma.Services;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.vlazma.Dto.ResponseData;
import com.vlazma.Dto.Chart.ChartRequest;
import com.vlazma.Dto.Chart.ChartResponse;
import com.vlazma.Models.Chart;
import com.vlazma.Models.ChartItem;
import com.vlazma.Models.Product;
import com.vlazma.Repositories.ChartItemRepository;
import com.vlazma.Repositories.ChartRepository;
import com.vlazma.Repositories.CustomersRepository;
import com.vlazma.Repositories.ProductRepository;

import java.util.List;

@Service
public class ChartService {
    @Autowired
    private ChartRepository chartRepository;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ChartItemRepository chartItemRepository;

    public ResponseEntity<ResponseData<ChartResponse>> create(ChartRequest chartRequest, Errors errors) {
        ResponseData<ChartResponse> responseData = new ResponseData<>();
        var customer = customersRepository.findById(0);
        try {
            customer = customersRepository.findById(Integer.parseInt(chartRequest.getCustomerId()));
        } catch (NumberFormatException e) {
        }
        if (errors.hasErrors() || customer.isEmpty()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(customer.isEmpty() ? "Customer Not Found" : null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        var chart = Chart.builder()
                .customer(customer.get())
                .checkOut(0)
                .grandTotal(0)
                .build();
        chartRepository.save(chart);
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(ChartResponse
                .builder()
                .id(chart.getId())
                .customerId(chart.getCustomer().getId())
                .checkOut(false)
                .grandTotal(chart.getGrandTotal())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    public ResponseEntity<ResponseData<List<ChartResponse>>> getAllCharts() {
        List<Chart> charts = chartRepository.findAll();
        ResponseData<List<ChartResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(charts.stream().map(this::mapToResponse).toList());

        return ResponseEntity.ok(responseData);
    }

    private ChartResponse mapToResponse(Chart chart) {
        return ChartResponse.builder()
                .id(chart.getId())
                .customerId(chart.getCustomer().getId())
                .checkOut(chart.getCheckOut() == 1 ? true : false)
                .grandTotal(chart.getGrandTotal())
                .build();
    }

    public ResponseEntity<ResponseData<ChartResponse>> findById(int id) {
        var chart = chartRepository.findById(id);
        ResponseData<ChartResponse> responseData = new ResponseData<>();
        if (chart.isEmpty()) {
            responseData.getMessages().add("Customer Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(ChartResponse
                .builder()
                .id(id)
                .customerId(chart.get().getCustomer().getId())
                .checkOut(chart.get().getCheckOut() == 1 ? true : false)
                .grandTotal(id)
                .build());
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<List<ChartResponse>>> currentChart(int id) {
        List<Chart> charts = chartRepository.findByCustomerId(id);
        ResponseData<List<ChartResponse>> responseData = new ResponseData<>();
        if (charts.isEmpty()) {
            responseData.getMessages().add("Customer Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        List<Chart> current = new ArrayList<>();
        for (Chart ch : charts) {
            if (ch.getCheckOut() == 0) {
                current.add(ch);
            }
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(current.stream().map(this::mapToResponse).toList());
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<List<ChartResponse>>> historyChart(int id) {
        List<Chart> charts = chartRepository.findByCustomerId(id);
        ResponseData<List<ChartResponse>> responseData = new ResponseData<>();
        if (charts.isEmpty()) {
            responseData.getMessages().add("Customer Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        List<Chart> current = new ArrayList<>();
        for (Chart ch : charts) {
            if (ch.getCheckOut() == 1) {
                current.add(ch);
            }
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(current.stream().map(this::mapToResponse).toList());
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<ChartResponse>> edit(int id, ChartRequest chartRequest, Errors errors) {
        var chart = chartRepository.findById(id);
        ResponseData<ChartResponse> responseData = new ResponseData<>();
        if (errors.hasErrors() || chart.isEmpty()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(chart.isEmpty() ? "Chart Not Found" : null);
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        Chart updatedChart = chart.get();
        updatedChart.setCheckOut(chartRequest.isCheckOut() ? 1 : 0);
        updatedChart.setGrandTotal(Integer.parseInt(chartRequest.getGrandTotal()));
        chartRepository.save(updatedChart);
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(ChartResponse
                .builder()
                .id(id)
                .customerId(updatedChart.getCustomer().getId())
                .checkOut(updatedChart.getCheckOut() == 1 ? true : false)
                .grandTotal(id)
                .build());

        return ResponseEntity.ok(responseData);
    }

    public void updateGrandTotal(int id, int gt) {
        var chart = chartRepository.findById(id);
        chart.get().setGrandTotal(gt);
        chartRepository.save(chart.get());
    }

    public ResponseEntity<ResponseData<ChartResponse>> checkOutChart(int id) {
        var chart = chartRepository.findById(id);
        ResponseData<ChartResponse> responseData = new ResponseData<>();
        if (chart.isEmpty()) {
            responseData.getMessages().add("Chart Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        chart.get().setCheckOut(1);
        chartRepository.save(chart.get());
        var chartItem = chartItemRepository.findAll();
        List<ChartItem> findChartItem = new ArrayList<>();
        for(var x:chartItem){
            if(x.getChart().getId() == id){
                findChartItem.add(x);
            }
        }
        List<Product> product = productRepository.findAll();
        List<Product> updatedProduct = new ArrayList<>();
        int buffer = 0;
        for (var x : product) {
            for (var y : findChartItem) {
                if (y.getProduct().getId() == x.getId()) {
                    buffer = x.getStock() - y.getQuantity();
                    x.setStock(buffer);
                    updatedProduct.add(x);
                }
            }
        }
        for(var x:updatedProduct){
            if(x.getStock()<1){
                x.setAvailable(0);
            }
        }
        productRepository.saveAll(updatedProduct);
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(ChartResponse.builder()
                .id(id)
                .customerId(chart.get().getCustomer().getId())
                .checkOut(true)
                .grandTotal(chart.get().getGrandTotal())
                .build());
        return ResponseEntity.ok(responseData);
    }

}
