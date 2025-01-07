// Cart Controller
package com.example.lista02.cart;

import com.example.lista02.product.Product;
import com.example.lista02.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;
    private final CartService cartService;

    public CartController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping
    public String viewCart(Model model, HttpServletRequest request) {
        List<CartItem> cartItems = cartService.getCartItems(request);
        cartItems.removeIf(item -> productService.getProductById(item.getProductId()) == null);

        cartItems.forEach(item -> {
            Product product = productService.getProductById(item.getProductId());
            item.setPrice(product.getPrice());
            item.setTotalPrice(product.getPrice() * item.getQuantity());
        });

        double total = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        return "cart/view";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId, HttpServletRequest request, HttpServletResponse response) {
        cartService.addToCart(response, request, productId, 1);
        return "redirect:/cart";
    }

    @PostMapping("/update/{productId}")
    public String updateQuantity(@PathVariable Long productId, @RequestParam int quantity,
                                 HttpServletRequest request, HttpServletResponse response) {
        cartService.updateQuantity(response, request, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId,
                                 HttpServletRequest request, HttpServletResponse response) {
        cartService.removeFromCart(response, request, productId);
        return "redirect:/cart";
    }
}
