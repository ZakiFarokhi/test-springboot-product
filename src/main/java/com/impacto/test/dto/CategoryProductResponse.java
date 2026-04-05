package com.impacto.test.dto;


import lombok.Data;

@Data
public class CategoryProductResponse {
    private Long id;
    private String code;
    private String name;
    private Double price;
    private Long categoryId;
}