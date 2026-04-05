package com.impacto.test.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private List<CategoryProductResponse> products;
}
