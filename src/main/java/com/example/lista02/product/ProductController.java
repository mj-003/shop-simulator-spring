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

    @GetMapping("/")
    public String home(Model model) {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                DateFormat.LONG, Locale.getDefault());
        String serverTime = dateFormat.format(date);
        model.addAttribute("serverTime", serverTime.toString() );
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("productList", productList);
        return "products/index";
    }

    @GetMapping("/seed")
    public String seed() {
        productService.seed();
        return "redirect:/products/";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Product product) {
        productService.createProduct(product);
        return "redirect:/products/";
    }

    @GetMapping("/details")
    public String details(@RequestParam("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/details";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Product product) {
        productService.updateProduct(product);
        return "redirect:/products/";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products/";
    }
}
