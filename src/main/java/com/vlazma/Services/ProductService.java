package com.vlazma.Services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.vlazma.Dto.ResponseData;
import com.vlazma.Dto.Product.ProductRequest;
import com.vlazma.Dto.Product.ProductResponse;
import com.vlazma.Models.Product;
import com.vlazma.Repositories.CategoryRepository;
import com.vlazma.Repositories.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<ResponseData<ProductResponse>> create(ProductRequest productRequest, Errors errors) {
        ResponseData<ProductResponse> responseData = new ResponseData<>();
        var category = categoryRepository.findById(0);
        try {
            category = categoryRepository.findById(Integer.parseInt(productRequest.getCategoryId()));
        } catch (NumberFormatException e) {
        }
        if (errors.hasErrors() || category.isEmpty()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(category.isEmpty() ? "Category Not Found" : null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        var product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .category(category.get())
                .price(Integer.parseInt(productRequest.getPrice()))
                .stock(Integer.parseInt(productRequest.getStock()))
                .available(1)
                .image(productRequest.getImage())
                .build();
        productRepository.save(product);
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(ProductResponse.builder()
                .id(product.getId())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .category(category.get().getName())
                .price(Integer.parseInt(productRequest.getPrice()))
                .stock(Integer.parseInt(productRequest.getStock()))
                .available(productRequest.isAvailable())
                .image(productRequest.getImage())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);

    }

    public ResponseEntity<ResponseData<List<ProductResponse>>> getAllProducts() {
        List<Product> product = productRepository.findAll();
        ResponseData<List<ProductResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(product.stream().map(this::mapToResponse).toList());

        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<List<ProductResponse>>> getAllProductsByCategoryId(int id) {
        List<Product> product = productRepository.findByAvailableAndCategoryId(1, id);
        ResponseData<List<ProductResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(product.stream().map(this::mapToResponse).toList());

        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<List<ProductResponse>>> getAllProductsByCategoryName(String name) {
        List<Product> product = productRepository.findByAvailableAndCategoryName(1, name);
        ResponseData<List<ProductResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(product.stream().map(this::mapToResponse).toList());

        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<List<ProductResponse>>> getAllProductsContains(String name) {
        List<Product> product = productRepository.findByAvailableAndNameContains(1, name);
        ResponseData<List<ProductResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(product.stream().map(this::mapToResponse).toList());

        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<List<ProductResponse>>> getAllAvailableProduct() {
        List<Product> product = productRepository.findByAvailable(1);
        ResponseData<List<ProductResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(product.stream().map(this::mapToResponse).toList());

        return ResponseEntity.ok(responseData);
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory().getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .available(product.getAvailable() == 1 ? true : false)
                .image(product.getImage())
                .build();
    }

    public ResponseEntity<ResponseData<ProductResponse>> findById(int id) {
        var product = productRepository.findById(id);
        ResponseData<ProductResponse> responseData = new ResponseData<>();
        if (product.isEmpty()) {
            responseData.getMessages().add("Product Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(ProductResponse.builder()
                .id(product.get().getId())
                .name(product.get().getName())
                .name(product.get().getName())
                .description(product.get().getDescription())
                .category(product.get().getCategory().getName())
                .price(product.get().getPrice())
                .stock(product.get().getStock())
                .available(product.get().getAvailable() == 1 ? true : false)
                .image(product.get().getImage())
                .build());

        return ResponseEntity.ok(responseData);

    }

    public ResponseEntity<ResponseData<ProductResponse>> edit(int id, ProductRequest productRequest, Errors errors) {
        var product = productRepository.findById(id);
        ResponseData<ProductResponse> responseData = new ResponseData<>();
        var category = categoryRepository.findById(0);
        try {
            category = categoryRepository.findById(Integer.parseInt(productRequest.getCategoryId()));
        } catch (NumberFormatException e) {
        }
        if (errors.hasErrors() || category.isEmpty() || product.isEmpty()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(product.isEmpty() ? "Product Not Found" : null);
            responseData.getMessages().add(category.isEmpty() ? "Category Not Found" : null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        Product myProduct = product.get();
        myProduct.setName(productRequest.getName());
        myProduct.setDescription(productRequest.getDescription());
        myProduct.setCategory(category.get());
        myProduct.setPrice(Integer.parseInt(productRequest.getPrice()));
        myProduct.setStock(Integer.parseInt(productRequest.getStock()));
        myProduct.setAvailable(productRequest.isAvailable() ? 1 : 0);
        myProduct.setImage(productRequest.getImage());
        productRepository.save(myProduct);
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(ProductResponse.builder()
                .id(myProduct.getId())
                .name(myProduct.getName())
                .description(myProduct.getDescription())
                .category(myProduct.getCategory().getName())
                .price(myProduct.getPrice())
                .stock(myProduct.getStock())
                .available(myProduct.getAvailable() == 1 ? true : false)
                .image(myProduct.getImage())
                .build());

        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<ProductResponse>> delete(int id) {
        var product = productRepository.findById(id);
        ResponseData<ProductResponse> responseData = new ResponseData<>();
        if (product.isEmpty()) {
            responseData.getMessages().add("Product Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
        }
        Product myProduct = product.get();
        myProduct.setAvailable(0);
        productRepository.save(myProduct);
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(
                ProductResponse.builder()
                        .id(myProduct.getId())
                        .name(myProduct.getName())
                        .description(myProduct.getDescription())
                        .category(myProduct.getCategory().getName())
                        .price(myProduct.getPrice())
                        .stock(myProduct.getStock())
                        .available(false)
                        .image(myProduct.getImage())
                        .build());
        return ResponseEntity.ok().body(responseData);
    }

    public void remove(int id) {
        List<Product> products = productRepository.findByCategoryId(id);
        productRepository.deleteAll(products);
    }

}
