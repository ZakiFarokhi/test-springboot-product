package com.impacto.test.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank(message = "Nama produk tidak boleh kosong")
    @Size(min = 3, max = 100, message = "Nama produk harus antara 3 - 100 karakter")
    private String name;

    @NotNull(message = "Harga tidak boleh kosong")
    @Min(value = 1, message = "Harga harus lebih besar dari 0")
    private Double price;

    @NotNull(message = "ID Kategori wajib diisi")
    private Long categoryId;

    private String code; // Boleh kosong karena ada logika auto-generate
}