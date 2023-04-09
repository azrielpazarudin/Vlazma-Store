package com.vlazma.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.vlazma.Dto.ResponseData;
import com.vlazma.Dto.Category.CategoryRequest;
import com.vlazma.Dto.Category.CategoryResponse;
import com.vlazma.Models.Category;
import com.vlazma.Repositories.CategoryRepository;

import java.util.Collections;
import java.util.List;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    public ResponseEntity<ResponseData<CategoryResponse>> create(CategoryRequest categoryRequest,Errors errors){
        ResponseData<CategoryResponse> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for(ObjectError err:errors.getAllErrors()){
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
            var category = Category.builder()
            .name(categoryRequest.getName())
            .description(categoryRequest.getDescription())
            .build();
            categoryRepository.save(category);
            responseData.getMessages().add("Succes");
            responseData.setStatus(true);
            responseData.setPayload(CategoryResponse.builder()
            .id(category.getId())
            .name(category.getName())
            .description(category.getDescription())
            .build());

            return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
            
    }

    public ResponseEntity<ResponseData<List<CategoryResponse>>> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        ResponseData<List<CategoryResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(categories.stream().map(this::mapToResponse).toList());
        return ResponseEntity.ok(responseData);
    }

    private CategoryResponse mapToResponse(Category category){
        return CategoryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .description(category.getDescription())
        .build();
    }
    
    public ResponseEntity<ResponseData<CategoryResponse>> findById(int id){
        var cat = categoryRepository.findById(id);
        ResponseData<CategoryResponse> responseData = new ResponseData<>();
        if(cat.isEmpty()){
            responseData.getMessages().add("Category Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(CategoryResponse.builder()
        .id(id)
        .name(cat.get().getName())
        .description(cat.get().getDescription())
        .build());
        return ResponseEntity.ok(responseData);
        
    }

    public ResponseEntity<ResponseData<CategoryResponse>> edit(int id,CategoryRequest categoryRequest,Errors errors){
        var updateCat = categoryRepository.findById(id);
        ResponseData<CategoryResponse> responseData = new ResponseData<>();
        if(updateCat.isEmpty()||errors.hasErrors()){
            for(ObjectError err:errors.getAllErrors()){
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(updateCat.isEmpty()?"Category Not Found":null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        updateCat.get().setName(categoryRequest.getName());
        updateCat.get().setDescription(categoryRequest.getDescription());
        categoryRepository.save(updateCat.get());
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(CategoryResponse.builder()
        .id(id)
        .name(updateCat.get().getName())
        .description(updateCat.get().getDescription())
        .build());
        return ResponseEntity.ok(responseData);

    }

    public ResponseEntity<ResponseData<CategoryResponse>> delete(int id){
        var updateCat = categoryRepository.findById(id);
        ResponseData<CategoryResponse> responseData = new ResponseData<>();
        if(updateCat.isEmpty()){
            responseData.getMessages().add("Category Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        productService.remove(id);
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(CategoryResponse.builder()
        .id(id)
        .name(updateCat.get().getName())
        .description(updateCat.get().getDescription())
        .build());
        categoryRepository.deleteById(id);
        return ResponseEntity.ok(responseData);
    }

    
}
