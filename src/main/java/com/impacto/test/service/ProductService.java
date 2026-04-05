package com.impacto.test.service;

import com.impacto.test.dto.ProductDTO;
import com.impacto.test.model.Product;
import com.impacto.test.repository.CategoryRepository;
import com.impacto.test.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public void saveProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        // Handle Category
        if (dto.getCategoryId() != null) {
            categoryRepository.findById(dto.getCategoryId()).ifPresent(product::setCategory);
        }

        // Logic Auto Generate / Increment Code
        if (dto.getCode() == null || dto.getCode().isEmpty()) {
            product.setCode("PROD-" + System.currentTimeMillis());
        } else {
            String finalCode = dto.getCode();
            while (productRepository.existsByCode(finalCode)) {
                finalCode = incrementString(finalCode);
            }
            product.setCode(finalCode);
        }
        productRepository.save(product);
    }

    private String incrementString(String code) {
        // Mencari angka di akhir string (misal PROD1 -> PROD2)
        Pattern pattern = Pattern.compile("(.*?)(\\d+)$");
        Matcher matcher = pattern.matcher(code);
        if (matcher.find()) {
            String prefix = matcher.group(1);
            int number = Integer.parseInt(matcher.group(2)) + 1;
            return prefix + number;
        }
        return code + "1"; // Jika tidak ada angka, tambah angka 1
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        return productRepository.findById(id).map(this::convertToDTO).orElseThrow();
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDTO(Product p) {
        ProductDTO d = new ProductDTO();
        d.setId(p.getId());
        d.setCode(p.getCode());
        d.setName(p.getName());
        d.setPrice(p.getPrice());
        if (p.getCategory() != null) {
            d.setCategoryId(p.getCategory().getId());
            d.setCategoryDesc(p.getCategory().getName());
        }
        return d;
    }
}