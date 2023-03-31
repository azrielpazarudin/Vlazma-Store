package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vlazma.Dto.CategoryDto;
import com.vlazma.Models.Category;
import com.vlazma.Repositories.CategoryRepository;


@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/find-all")
    public Object findAll(){
        return categoryRepository.findAll();
    }

    @PostMapping("/")
    public Object create (@RequestBody CategoryDto categoryDto){
    Category category = new Category();

    category.setName(categoryDto.getName());
    category.setDescription(categoryDto.getDescription());

    return ResponseEntity.ok(categoryRepository.save(category));
    }
    
    @PutMapping("/{id}")
    public Object update(@PathVariable Integer id, @RequestBody CategoryDto categoryDto){
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            return ResponseEntity.badRequest().body("id tidak ditemukan!");
        }

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return categoryRepository.save(category);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable Integer id){
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            return ResponseEntity.badRequest().body("id tidak ditemukan!");
        }

        categoryRepository.delete(category);
        return null;
    }

}
