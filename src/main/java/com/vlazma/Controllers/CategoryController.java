package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Dto.Category.CategoryRequest;
import com.vlazma.Services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public Object get() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable int id) {
        return categoryService.findById(id);
    }

    @Operation(summary = "Adding Cateogry Data", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/")
    public Object create(HttpServletRequest request, @Valid @RequestBody CategoryRequest categoryRequest,
            Errors errors) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return categoryService.create(categoryRequest, errors);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin Can Acces It");
    }

    @Operation(summary = "Editing Category Data", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/edit/{id}")
    public Object edit(HttpServletRequest request, @PathVariable int id,
            @Valid @RequestBody CategoryRequest categoryRequest, Errors errors) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return categoryService.edit(id, categoryRequest, errors);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin Can Acces It");

    }

}
