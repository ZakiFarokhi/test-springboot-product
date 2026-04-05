package com.impacto.test.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String code;
    private String name;
    private Double price;
    private Long categoryId;
    private String categoryDesc;
}
