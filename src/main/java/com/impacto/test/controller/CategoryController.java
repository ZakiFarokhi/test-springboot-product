package com.impacto.test.controller;

import com.impacto.test.dto.CategoryDTO;
import com.impacto.test.dto.CategoryRequest;
import com.impacto.test.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> list() {
        return categoryService.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addCategory(@RequestBody CategoryRequest request) {
        categoryService.createCategory(request);
        return ResponseEntity.ok(Map.of("message", "Category created successfully"));
    }
}