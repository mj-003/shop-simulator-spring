// Cart Controller
package com.example.lista02.cart;

import com.example.lista02.product.Product;
import com.example.lista02.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    public CartController(ProductService productService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public String viewCart(Model model, HttpServletRequest request) {
        List<CartItem> cartItems = getCartItemsFromCookie(request);
        double total = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "cart/view";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId, HttpServletRequest request, HttpServletResponse response) {
        Product product = productService.getProductById(productId);
        List<CartItem> cartItems = getCartItemsFromCookie(request);

        boolean found = false;
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + 1);
                item.setTotalPrice(item.getPrice() * item.getQuantity());
                found = true;
                break;
            }
        }

        if (!found) {
            CartItem newItem = new CartItem();
            newItem.setProductId(productId);
            newItem.setProductName(product.getName());
            newItem.setPrice(product.getPrice());
            newItem.setQuantity(1);
            newItem.setTotalPrice(product.getPrice());
            cartItems.add(newItem);
        }

        saveCartItemsToCookie(cartItems, response);
        return "redirect:/cart";
    }

    @PostMapping("/update/{productId}")
    public String updateQuantity(@PathVariable Long productId,
                                 @RequestParam int quantity,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        List<CartItem> cartItems = getCartItemsFromCookie(request);

        if (quantity <= 0) {
            cartItems.removeIf(item -> item.getProductId().equals(productId));
        } else {
            for (CartItem item : cartItems) {
                if (item.getProductId().equals(productId)) {
                    item.setQuantity(quantity);
                    item.setTotalPrice(item.getPrice() * quantity);
                    break;
                }
            }
        }

        saveCartItemsToCookie(cartItems, response);
        return "redirect:/cart";
    }

    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        List<CartItem> cartItems = getCartItemsFromCookie(request);
        cartItems.removeIf(item -> item.getProductId().equals(productId));
        saveCartItemsToCookie(cartItems, response);
        return "redirect:/cart";
    }

    private List<CartItem> getCartItemsFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cart".equals(cookie.getName())) {
                    try {
                        String decodedValue = new String(Base64.getDecoder().decode(cookie.getValue()));
                        return objectMapper.readValue(decodedValue, new TypeReference<List<CartItem>>() {});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    private void saveCartItemsToCookie(List<CartItem> cartItems, HttpServletResponse response) {
        try {
            String cartJson = objectMapper.writeValueAsString(cartItems);
            String encodedCart = Base64.getEncoder().encodeToString(cartJson.getBytes());
            Cookie cookie = new Cookie("cart", encodedCart);
            cookie.setPath("/");
            cookie.setMaxAge(86400); // 24 hours
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}