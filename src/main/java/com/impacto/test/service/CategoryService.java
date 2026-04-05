package com.impacto.test.service;

import com.impacto.test.dto.CategoryDTO;
import com.impacto.test.dto.CategoryProductResponse;
import com.impacto.test.dto.CategoryRequest;
import com.impacto.test.model.Category;
import com.impacto.test.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        categoryRepository.save(category);
    }

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream().map(cat -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(cat.getId());
            dto.setName(cat.getName());

            // Map products list inside category
            if (cat.getProducts() != null) {
                List<CategoryProductResponse> productResponses = cat.getProducts().stream().map(p -> {
                    CategoryProductResponse pr = new CategoryProductResponse();
                    pr.setId(p.getId());
                    pr.setCode(p.getCode());
                    pr.setName(p.getName());
                    pr.setPrice(p.getPrice());
                    pr.setCategoryId(cat.getId());
                    return pr;
                }).collect(Collectors.toList());
                dto.setProducts(productResponses);
            }
            return dto;
        }).collect(Collectors.toList());
    }
}