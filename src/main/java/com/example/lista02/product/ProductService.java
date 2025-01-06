package com.example.lista02.product;

import com.example.lista02.category.Category;
import com.example.lista02.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Seed danych do bazy
    public void seed() {
        Category category1 = categoryRepository.save(new Category("Pieczywo", "K1"));
        Category category2 = categoryRepository.save(new Category("Nabiał", "K2"));
        Category category3 = categoryRepository.save(new Category("Mięso", "K3"));

        Product product1 = Product.builder()
                .name("Chleb")
                .weight(1.0)
                .price(5.20)
                .index(12345)
                .category(category1)
                .build();
        productRepository.save(product1);

        Product product2 = Product.builder()
                .name("Masło")
                .weight(0.25)
                .price(7.00)
                .index(12346)
                .category(category2)
                .build();
        productRepository.save(product2);

        Product product3 = Product.builder()
                .name("Kiełbasa")
                .weight(0.5)
                .price(12.50)
                .index(12347)
                .category(category3)
                .build();
        productRepository.save(product3);

        Product product4 = Product.builder()
                .name("Mleko")
                .weight(1.0)
                .price(3.80)
                .index(12348)
                .category(category2)
                .build();
        productRepository.save(product4);

        Product product5 = Product.builder()
                .name("Chleb pełnoziarnisty")
                .weight(1.2)
                .price(6.50)
                .index(12349)
                .category(category1)
                .build();
        productRepository.save(product5);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }


}
