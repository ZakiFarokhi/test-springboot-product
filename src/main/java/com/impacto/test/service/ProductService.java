package com.impacto.test.service;

import com.impacto.test.dto.ProductDTO;
import com.impacto.test.dto.ProductRequest;
import com.impacto.test.model.Product;
import com.impacto.test.repository.CategoryRepository;
import com.impacto.test.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j; // Tambahkan ini untuk logging
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void saveProduct(ProductRequest dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        // Mapping Category
        if (dto.getCategoryId() != null) {
            categoryRepository.findById(dto.getCategoryId())
                    .ifPresentOrElse(product::setCategory,
                            () -> { throw new RuntimeException("Category ID tidak ditemukan"); });
        }

        // Logic Auto-Code & Increment
        String finalCode;
        if (dto.getCode() == null || dto.getCode().trim().isEmpty()) {
            // Default prefix jika kosong
            finalCode = "PROD-" + (System.currentTimeMillis() % 1000000);
        } else {
            finalCode = dto.getCode().toUpperCase(); // Standarisasi Uppercase
            while (productRepository.existsByCode(finalCode)) {
                finalCode = incrementString(finalCode);
            }
        }

        product.setCode(finalCode);
        productRepository.save(product);
        log.info("Produk berhasil disimpan dengan kode: {}", finalCode);
    }

    /**
     * Logika Increment dengan Padding Nol (e.g. MOU-001 -> MOU-002)
     */
    private String incrementString(String code) {
        // Regex untuk memisahkan prefix dan angka di akhir
        Pattern pattern = Pattern.compile("^(.*?)(\\d+)$");
        Matcher matcher = pattern.matcher(code);

        if (matcher.find()) {
            String prefix = matcher.group(1);      // Contoh: "MOU-"
            String numberStr = matcher.group(2);   // Contoh: "001"
            int length = numberStr.length();       // Simpan panjang digit asli

            int nextNumber = Integer.parseInt(numberStr) + 1;

            // Format kembali dengan padding nol sesuai panjang asli
            // %03d artinya minimal 3 digit, diisi nol di depan
            String format = "%0" + length + "d";
            return prefix + String.format(format, nextNumber);
        }

        // Jika input "MOU", maka jadikan "MOU-001"
        return code + "-001";
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Product tidak ditemukan"));
    }

    @Transactional
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