// Cart Entity
package com.example.lista02.cart;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartItem {
    private Long productId;
    private String productName;
    private double price;
    private int quantity;
    private double totalPrice;
}