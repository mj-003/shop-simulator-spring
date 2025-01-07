package com.example.lista02.product;

import com.example.lista02.category.Category;
import lombok.*;

import javax.persistence.*;
import java.util.Base64;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double weight;
    private double price;
    private int index;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    public String getImageBase64() {
        return image != null ? Base64.getEncoder().encodeToString(image) : null;
    }

    @ManyToOne
    private Category category;

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', weight=" + weight + ", price=" + price + "}";
    }
}
