package com.example.lista02.product;

import com.example.lista02.category.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("productList", productList);
        return "products/list";
    }

    @GetMapping("/index")
    public String products(Model model) {
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
    public String add(@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            product.setImage(file.getBytes());
        }
        productService.createProduct(product);
        return "redirect:/products/index";
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
    public String edit(@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            product.setImage(file.getBytes());
        } else {
            // Preserve existing image if no new one uploaded
            Product existingProduct = productService.getProductById(product.getId());
            if (existingProduct != null) {
                product.setImage(existingProduct.getImage());
            }
        }
        productService.updateProduct(product);
        return "redirect:/products/index";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products/index";
    }
}
