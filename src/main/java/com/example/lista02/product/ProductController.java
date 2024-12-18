package com.example.lista02.product;

import com.example.lista02.category.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // Strona główna dla produktów
    @GetMapping("/")
    public String home(Model model) {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                DateFormat.LONG, Locale.getDefault());
        String serverTime = dateFormat.format(date);
        model.addAttribute("serverTime", serverTime.toString() );
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("productList", productList);
        return "products/index"; // Widok z listą produktów
    }

    // Seed danych przykładowych
    @GetMapping("/seed")
    public String seed() {
        productService.seed(); // Dodanie przykładowych produktów
        return "redirect:/products/"; // Przekierowanie na stronę główną produktów
    }

    // Strona do dodawania produktu
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories()); // Przekazanie listy kategorii
        return "products/add"; // Widok do dodawania produktu
    }

    // Dodanie nowego produktu
    @PostMapping("/add")
    public String add(@ModelAttribute Product product) {
        productService.createProduct(product); // Tworzenie nowego produktu
        return "redirect:/products/"; // Przekierowanie na stronę z listą produktów
    }

    // Strona szczegółów produktu
    @GetMapping("/details")
    public String details(@RequestParam("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/details"; // Widok szczegółów produktu
    }

    // Edycja produktu (GET)
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/edit"; // Widok edycji produktu
    }

    // Edycja produktu (POST)
    @PostMapping("/edit")
    public String edit(@ModelAttribute Product product) {
        productService.updateProduct(product); // Aktualizacja produktu
        return "redirect:/products/"; // Przekierowanie na stronę z listą produktów
    }

    // Usunięcie produktu
    @GetMapping("/remove")
    public String remove(@RequestParam("id") Long id) {
        productService.deleteProduct(id); // Usunięcie produktu
        return "redirect:/products/"; // Przekierowanie na stronę z listą produktów
    }
}
