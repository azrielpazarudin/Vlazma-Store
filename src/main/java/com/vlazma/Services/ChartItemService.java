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
import com.vlazma.Repositories.ProductRepository;

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

    public ResponseEntity<ResponseData<ChartItemResponse>> create(ChartItemRequest chartItemRequest, Errors errors) {
        var chart = chartRepository.findById(0);
        var product = productRepository.findById(0);
        ResponseData<ChartItemResponse> responseData = new ResponseData<>();
        try {
            chart = chartRepository.findById(Integer.parseInt(chartItemRequest.getChartId()));
            product = productRepository.findById(Integer.parseInt(chartItemRequest.getProductId()));
        } catch (NumberFormatException e) {
        }
        if (errors.hasErrors() || chart.isEmpty() || product.isEmpty()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(chart.isEmpty() ? "Chart Not Found" : null);
            responseData.getMessages().add(product.isEmpty() ? "Product Not Found" : null);
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
        List<ChartItem> sameChart = new ArrayList<>();
        for (var x : findChart) {
            if (x.getChart().getId() == chartItem.getChart().getId()) {
                sameChart.add(x);
            }
        }
        int total = 0;
        for (var sp : sameChart) {
            if (sp.getProduct().getId() == chartItem.getProduct().getId()) {
                ChartItem chartSameProduct = sp;
                sp.setQuantity(sp.getQuantity() + chartItem.getQuantity());
                chartItem.getProduct();
                chartItem = chartSameProduct;
            }
            total += (sp.getProduct().getPrice() * sp.getQuantity());
        }
        if(chartItem.getQuantity()>chartItem.getProduct().getStock()){
            responseData.getMessages().add("Only "+chartItem.getProduct().getStock()+" Left");
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }
        chartItemRepository.save(chartItem);
        chartService.updateGrandTotal(chartItem.getChart().getId(), total);
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
}
