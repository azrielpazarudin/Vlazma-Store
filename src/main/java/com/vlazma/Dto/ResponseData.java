package com.vlazma.Dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class ResponseData <R> {
    private boolean status;
    private List<String> messages = new ArrayList<>();
    private R payload;
}  
