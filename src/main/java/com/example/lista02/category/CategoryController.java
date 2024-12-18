package com.example.lista02.category;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                DateFormat.LONG, Locale.getDefault());
        String serverTime = dateFormat.format(date);
        model.addAttribute("serverTime", serverTime.toString() );

        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        return "categories/index"; // Widok z listą kategorii
    }

    @GetMapping("/seed")
    public String seed() {
        categoryService.seed();
        return "redirect:/categories/";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("category", new Category());
        return "categories/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Category category) {
        categoryService.createCategory(category);
        return "redirect:/categories/";
    }

    @GetMapping("/details")
    public String details(@RequestParam("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "categories/details";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "categories/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Category category) {
        categoryService.updateCategory(category);
        return "redirect:/categories/";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("id") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories/";
    }
}
