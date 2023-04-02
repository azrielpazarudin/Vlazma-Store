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

import com.vlazma.Dto.Category.CategoryRequest;
import com.vlazma.Services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public Object get(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable int id){
        return categoryService.findById(id);
    }

    @PostMapping("/")
    public Object create(@Valid@RequestBody CategoryRequest categoryRequest,Errors errors){
        return categoryService.create(categoryRequest, errors);
    }

    @PostMapping("/{id}")
    public Object edit(@PathVariable int id,@Valid@RequestBody CategoryRequest categoryRequest,Errors errors){
        return categoryService.edit(id, categoryRequest, errors);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id){
        return categoryService.delete(id);
    }

    
}
