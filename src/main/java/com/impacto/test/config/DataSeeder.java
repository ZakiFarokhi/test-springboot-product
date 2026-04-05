package com.impacto.test.config;

import com.impacto.test.model.Category;
import com.impacto.test.model.Product;
import com.impacto.test.repository.CategoryRepository;
import com.impacto.test.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(CategoryRepository categoryRepository,
                                   ProductRepository productRepository) {
        return args -> {
            // 1. Cek jika data sudah ada untuk menghindari duplikasi saat restart docker
            if (categoryRepository.count() == 0) {

                // 2. Buat Dummy Categories
                Category cat1 = new Category();
                cat1.setName("Elektronik");

                Category cat2 = new Category();
                cat2.setName("Peralatan Rumah Tangga");

                categoryRepository.saveAll(List.of(cat1, cat2));

                // 3. Buat Dummy Products
                Product p1 = new Product();
                p1.setName("Laptop ASUS ROG");
                p1.setPrice(15000000.0);
                p1.setCode("PRD-ASUS001");
                p1.setCategory(cat1);

                Product p2 = new Product();
                p2.setName("Mouse Logitek Wireless");
                p2.setPrice(450000.0);
                p2.setCode("MOU-001");
                p2.setCategory(cat1);

                Product p3 = new Product();
                p3.setName("Air Fryer Philips");
                p3.setPrice(1200000.0);
                p3.setCode("AF-001");
                p3.setCategory(cat2);

                productRepository.saveAll(List.of(p1, p2, p3));

                System.out.println("✅ Data Seeder: Berhasil memasukkan dummy data.");
            } else {
                System.out.println("ℹ️ Data Seeder: Data sudah ada, melewati proses seeding.");
            }
        };
    }
}