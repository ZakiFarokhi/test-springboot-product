package com.impacto.test.controller;

import com.impacto.test.dto.ProductDTO;
import com.impacto.test.dto.ProductRequest;
import com.impacto.test.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest dto) {
        productService.saveProduct(dto);
        return ResponseEntity.ok(Map.of("message", "Product created successfully"));
    }

    @GetMapping
    public List<ProductDTO> list() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductDTO get(@PathVariable Long id) {
        return productService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Product deleted"));
    }
}
