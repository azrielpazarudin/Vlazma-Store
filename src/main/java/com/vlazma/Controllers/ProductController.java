package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Dto.Product.ProductRequest;
import com.vlazma.Services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(summary = "Showing Product Data", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/")
    public Object get(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_CUSTOMER")) {
            return productService.getAllAvailableProduct();
        }
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable int id) {
        return productService.findById(id);
    }

    @Operation(summary = "Showing ProductData by category id", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/category-id/{id}")
    public Object getByCategoryId(HttpServletRequest httpServletRequest, @PathVariable int id) {
        return productService.getAllProductsByCategoryId(id);
    }

    @Operation(summary = "Showing ProductData by name", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/category-name/{name}")
    public Object getByCategoryName(HttpServletRequest request, @PathVariable String name) {
        return productService.getAllProductsByCategoryName(name);
    }

    @Operation(summary = "Showing ProductData by Category Name", security = {
            @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/name-contains/{like}")
    public Object getByNameContains(HttpServletRequest request, @PathVariable String like) {
        return productService.getAllProductsContains(like);
    }

    @Operation(summary = "Adding Product Data", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/")
    public Object create(HttpServletRequest request, @Valid @RequestBody ProductRequest productRequest, Errors errors) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return productService.create(productRequest, errors);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin Can Acces It");
    }

    @Operation(summary = "Showing ProductData by name", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/edit/{id}")
    public Object edit(HttpServletRequest request, @PathVariable int id,
            @Valid @RequestBody ProductRequest productRequest, Errors errors) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return productService.edit(id, productRequest, errors);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin Can Acces It");
    }

    @Operation(summary = "Showing ProductData by name", security = { @SecurityRequirement(name = "bearer-key") })
    @DeleteMapping("/delete/{id}")
    public Object delete(HttpServletRequest request, @PathVariable int id) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return productService.delete(id);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin Can Acces It");
    }

}
