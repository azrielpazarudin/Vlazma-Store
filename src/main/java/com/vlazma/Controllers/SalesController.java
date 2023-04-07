package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Repositories.SalesRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/vlazma/sales")
public class SalesController {
    @Autowired
    private SalesRepository salesRepository;
    @GetMapping("/")
    public Object showSales(HttpServletRequest request){
        if(request.isUserInRole("ROLE_ADMIN")){
            return ResponseEntity.ok(salesRepository.findAll());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin Can Acces It");
    }
}
