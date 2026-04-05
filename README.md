# 📦 Product & Category Management System (Spring Boot 21)

Sistem Backend RESTful API modern yang dibangun dengan **Java 21** dan **Spring Boot 3.x**. Proyek ini mendemonstrasikan integrasi antara Relational Database (PostgreSQL), kontainerisasi (Docker), dan dokumentasi API otomatis (OpenAPI/Swagger).

---

## 🌟 Fitur Utama

### 1. Manajemen Produk & Kategori
* **CRUD Lengkap**: Operasi Create, Read, Update, dan Delete untuk Produk.
* **Relasi Database**: Implementasi relasi Many-to-One antara Produk dan Kategori.
* **Category List with Products**: Endpoint kategori yang menampilkan daftar produk terkait di dalamnya secara otomatis.

### 2. Logika Bisnis Pintar (Auto-Code Logic)
Sistem memiliki kecerdasan buatan sederhana untuk menangani kode produk:
* **Auto-Generate**: Jika field `code` dikirim kosong atau null, sistem akan menghasilkan kode unik (Format: `PRD-<timestamp>`).
* **Smart Increment**: Jika `code` yang diinput secara manual sudah ada di database, sistem secara otomatis menambahkan angka di belakangnya (Contoh: `MOU-001` menjadi `MOU-002`, `MOU-003`, dst) untuk menghindari error duplikat.

### 3. Infrastruktur & DevOps
* **Java 21 Ready**: Menggunakan fitur terbaru dari JDK 21 (Eclipse Temurin).
* **Dockerized Environment**: Dilengkapi dengan `docker-compose.yml` untuk orkestrasi aplikasi, database, dan alat manajemen DB (pgAdmin).
* **Data Seeding**: Otomatis mengisi data awal (Elektronik, Peralatan Rumah Tangga, dll) saat aplikasi pertama kali dijalankan agar sistem tidak kosong.

---

## 🛠️ Stack Teknologi

| Komponen | Teknologi |
| :--- | :--- |
| **Bahasa Pemrograman** | Java 21 |
| **Framework Utama** | Spring Boot 3.x |
| **Persistence** | Spring Data JPA (Hibernate) |
| **Database** | PostgreSQL 15 (Alpine Edition) |
| **API Documentation** | Swagger UI / Springdoc OpenAPI 2.3.0 |
| **Build Tool** | Maven (3.9.x) |
| **Containerization** | Docker & Docker Compose 3.9 |
| **DB Management** | pgAdmin 4 |

---

## 📂 Struktur Proyek

```text
src/main/java/com/example/productcrud/
├── config/       # Konfigurasi Swagger & DataSeeder (Auto-fill data saat startup)
├── controller/   # REST Endpoints (/product & /category)
├── dto/          # Data Transfer Objects (Request/Response Mapping)
├── model/        # JPA Entities (Mapping Table ke Database)
├── repository/   # Interface Database (JPA Repository)
└── service/      # Logika Bisnis (Implementasi Auto-code & Increment logic)
