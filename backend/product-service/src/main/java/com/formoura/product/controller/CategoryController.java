package com.formoura.product.controller;


import com.formoura.product.category.dto.request.CategoryRequest;
import com.formoura.product.category.dto.response.CategoryResponse;
import com.formoura.product.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category API", description = "Category Management APIs")
public class CategoryController {

    private final CategoryService service;

    @Operation(summary = "Create Category")
    @PostMapping
    public CategoryResponse createCategory(
            @Valid @RequestBody CategoryRequest request) {

        return service.createCategory(request);
    }

    @Operation(summary = "Update Category")
    @PutMapping("/{id}")
    public CategoryResponse updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {

        return service.updateCategory(id, request);
    }


    @Operation(summary = "Get Category By Id")
    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(
            @PathVariable Long id) {

        return service.getCategoryById(id);
    }

    @Operation(summary = "Get all Category")
    @GetMapping
    public List<CategoryResponse> getAllCategories() {

        return service.getAllCategories();
    }

    @Operation(summary = "Delete Category")
    @DeleteMapping("/{id}")
    public void deleteCategory(
            @PathVariable Long id) {

        service.deleteCategory(id);
    }
}