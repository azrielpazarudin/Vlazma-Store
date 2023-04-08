package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public Object get(){
        return productService.getAllProducts();
    }

    @GetMapping("/available-product")
    public Object getAvailable(){
        return productService.getAllAvailableProduct();
    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable int id){
        return productService.findById(id);
    }

    @GetMapping("/category-id/{id}")
    public Object getByCategoryId(@PathVariable int id){
        return productService.getAllProductsByCategoryId(id);
    }

    @GetMapping("/category-name/{name}")
    public Object getByCategoryName(@PathVariable String name){
        return productService.getAllProductsByCategoryName(name);
    }

    @GetMapping("/name-contains/{like}")
    public Object getByNameContains(@PathVariable String like){
        return productService.getAllProductsContains(like);
    }

    @PostMapping("/")
    public Object create(@Valid@RequestBody ProductRequest productRequest,Errors errors){
        return productService.create(productRequest, errors);
    }

    @PostMapping("/{id}")
    public Object edit(@PathVariable int id,@Valid@RequestBody ProductRequest productRequest,Errors errors){
        return productService.edit(id, productRequest, errors);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id){
        return productService.delete(id);
    }




}
