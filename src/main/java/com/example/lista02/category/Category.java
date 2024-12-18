package com.example.lista02.category;

import com.example.lista02.product.Product;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor // Dodanie konstruktora bezargumentowego
@AllArgsConstructor // Konstruktor z wszystkimi polami
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;


    public Category(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Category{id=" + id + ", name='" + name + "', code='" + code + "'}";
    }
}
