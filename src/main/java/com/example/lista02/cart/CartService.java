package com.example.lista02.cart;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    private final String CART_COOKIE_NAME = "shopping_cart";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void addToCart(HttpServletResponse response,
                          HttpServletRequest request,
                          Long productId,
                          int quantity) {
        Map<Long, Integer> cart = getCartFromCookie(request);
        cart.merge(productId, quantity, Integer::sum);
        saveCartToCookie(response, cart);
    }

    public void updateQuantity(HttpServletResponse response,
                               HttpServletRequest request,
                               Long productId,
                               int quantity) {
        Map<Long, Integer> cart = getCartFromCookie(request);
        if (quantity > 0) {
            cart.put(productId, quantity);
        } else {
            cart.remove(productId);
        }
        saveCartToCookie(response, cart);
    }

    public void removeFromCart(HttpServletResponse response,
                               HttpServletRequest request,
                               Long productId) {
        Map<Long, Integer> cart = getCartFromCookie(request);
        cart.remove(productId);
        saveCartToCookie(response, cart);
    }

    private Map<Long, Integer> getCartFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, CART_COOKIE_NAME);
        if (cookie != null) {
            try {
                return objectMapper.readValue(
                        URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8),
                        new TypeReference<Map<Long, Integer>>() {}
                );
            } catch (IOException e) {
                return new HashMap<>();
            }
        }
        return new HashMap<>();
    }

    private void saveCartToCookie(HttpServletResponse response,
                                  Map<Long, Integer> cart) {
        try {
            String cartJson = objectMapper.writeValueAsString(cart);
            Cookie cookie = new Cookie(CART_COOKIE_NAME,
                    URLEncoder.encode(cartJson, StandardCharsets.UTF_8));
            cookie.setMaxAge(7 * 24 * 60 * 60); // 1 week
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (IOException e) {
            throw new RuntimeException("Error saving cart to cookie", e);
        }
    }

    public List<CartItem> getCartItems(HttpServletRequest request) {
        Map<Long, Integer> cart = getCartFromCookie(request);
        return null; // TODO: implement
    }
}
