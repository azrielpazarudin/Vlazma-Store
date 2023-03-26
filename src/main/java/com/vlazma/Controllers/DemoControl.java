package com.vlazma.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vlazma")
public class DemoControl {
    @GetMapping
    public String hey(){
        return "Hey";
    }
}
