package com.vlazma.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.vlazma.Dto.ResponseData;
import com.vlazma.Dto.ChartItem.ChartItemRequest;
import com.vlazma.Dto.ChartItem.ChartItemResponse;
import com.vlazma.Models.ChartItem;
import com.vlazma.Repositories.ChartItemRepository;
import com.vlazma.Repositories.ChartRepository;
import com.vlazma.Repositories.CustomersRepository;
import com.vlazma.Repositories.ProductRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ChartItemService {
    @Autowired
    private ChartItemRepository chartItemRepository;
    @Autowired
    private ChartRepository chartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ChartService chartService;
    @Autowired
    private CustomersRepository customersRepository;

    public ResponseEntity<ResponseData<ChartItemResponse>> create(ChartItemRequest chartItemRequest, Errors errors) {
        var chart = chartRepository.findById(0);
        var product = productRepository.findById(0);
        ResponseData<ChartItemResponse> responseData = new ResponseData<>();
        try {
            chart = chartRepository.findById(Integer.parseInt(chartItemRequest.getChartId()));
            product = productRepository.findById(Integer.parseInt(chartItemRequest.getProductId()));
        } catch (NumberFormatException e) {
        }
        if (errors.hasErrors() || chart.isEmpty() || product.isEmpty()
                || Integer.parseInt(chartItemRequest.getQuantity()) < 1||product.get().getAvailable()==0) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(chart.isEmpty() ? "Chart Not Found" : null);
            
            responseData.getMessages()
                    .add(Integer.parseInt(chartItemRequest.getQuantity()) < 1 ? "Minimum quantity Is One" : null);
            responseData.getMessages().add(product.isEmpty() ? "Product Not Found" : null);
            responseData.getMessages().add(product.get().getAvailable()==0 ? "Produk Is Not Available" : null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }
        ChartItem chartItem = new ChartItem();
        chartItem.setChart(chart.get());
        chartItem.setProduct(product.get());
        chartItem.setQuantity(Integer.parseInt(chartItemRequest.getQuantity()));
        var findChart = chartItemRepository.findAll();
        for (var x : findChart) {
            if (chartItem.getChart().getId() == x.getChart().getId()
                    && chartItem.getProduct().getId() == x.getProduct().getId()) {
                chartItem.setQuantity(chartItem.getQuantity() + x.getQuantity());
            }
        }
        if (chartItem.getQuantity() > chartItem.getProduct().getStock()) {
            responseData.getMessages().add("Maximum Ammount Is " + chartItem.getProduct().getStock());
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);

        }
        chartItemRepository.save(chartItem);
        var myChart = chartRepository.findAll();
        findChart = chartItemRepository.findAll();
        int total = 0;
        for (var x : myChart) {
            for (var y : findChart) {
                if (y.getChart().getId() == x.getId()) {
                    total += y.getQuantity() * y.getProduct().getPrice();
                    chartService.updateGrandTotal(x.getId(), total);
                }
            }
            total = 0;
        }

        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(ChartItemResponse.builder()
                .chartId(chartItem.getChart().getId())
                .productId(chartItem.getProduct().getId())
                .productName(chartItem.getProduct().getName())
                .productPrice(chartItem.getProduct().getPrice())
                .quantity(chartItem.getQuantity())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    public ResponseEntity<ResponseData<List<ChartItemResponse>>> getAllChartItems() {
        List<ChartItem> chartItems = chartItemRepository.findAll();
        ResponseData<List<ChartItemResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(chartItems.stream().map(this::mapToResponse).toList());
        return ResponseEntity.ok(responseData);
    }

    private ChartItemResponse mapToResponse(ChartItem chartItem) {
        return ChartItemResponse.builder()
                .chartId(chartItem.getChart().getId())
                .productId(chartItem.getProduct().getId())
                .productName(chartItem.getProduct().getName())
                .productPrice(chartItem.getProduct().getPrice())
                .quantity(chartItem.getQuantity())
                .build();
    }

    public ResponseEntity<ResponseData<ChartItemResponse>> editCurrentChartProduct(int id, int product,
            int newQuantity) {
        var findChart = chartItemRepository.findAll();
        var sameChart = new ChartItem();
        for (var x : findChart) {
            if (x.getChart().getId() == id && x.getProduct().getId() == product) {
                sameChart = x;
            }
        }
        sameChart.setQuantity(newQuantity);
        ResponseData<ChartItemResponse> responseData = new ResponseData<>();

        if (sameChart.getQuantity() > sameChart.getProduct().getStock()) {
            responseData.getMessages().add("Maximum Ammount Is " + sameChart.getProduct().getStock());
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        } else {

            chartItemRepository.save(sameChart);
        }
        var myChart = chartRepository.findAll();
        findChart = chartItemRepository.findAll();
        int total = 0;
        for (var x : myChart) {
            for (var y : findChart) {
                if (y.getChart().getId() == x.getId()) {
                    total += y.getQuantity() * y.getProduct().getPrice();
                    chartService.updateGrandTotal(x.getId(), total);
                }
            }
            total = 0;
        }
        if (sameChart.getQuantity() < 1) {
            deleteProductFromChart(sameChart);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(ChartItemResponse.builder()
                .chartId(sameChart.getChart().getId())
                .productId(sameChart.getProduct().getId())
                .productName(sameChart.getProduct().getName())
                .productPrice(sameChart.getProduct().getPrice())
                .quantity(sameChart.getQuantity())
                .build());
        return ResponseEntity.ok(responseData);

    }

    public ResponseEntity<ResponseData<List<ChartItemResponse>>> currentChartItem(HttpServletRequest request){
        var customers = customersRepository.findByUserEmail(request.getUserPrincipal().getName().toString()).orElseThrow();
        List<ChartItem> chartItems = chartItemRepository.findAll();
        List<ChartItem> current = new ArrayList<>();
        for(var x:chartItems){
            if(x.getChart().getCustomer().getId()==customers.getId()&&x.getChart().getCheckOut()!=0){
                current.add(x);
            }
        }
        ResponseData<List<ChartItemResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(current.stream().map(this::mapToResponse).toList());
        return ResponseEntity.ok(responseData);
    }
    
    public void deleteProductFromChart(ChartItem chartItem) {
        chartItemRepository.delete(chartItem);
    }
}
