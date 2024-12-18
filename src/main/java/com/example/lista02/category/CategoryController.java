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

    // Strona główna dla kategorii (lista kategorii)
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

    // Seed danych przykładowych
    @GetMapping("/seed")
    public String seed() {
        categoryService.seed(); // Dodanie przykładowych kategorii
        return "redirect:/categories/"; // Przekierowanie na stronę główną kategorii
    }

    // Strona do dodawania kategorii
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("category", new Category()); // Przekazanie pustego obiektu kategorii
        return "categories/add"; // Widok do dodawania kategorii
    }

    // Dodanie nowej kategorii
    @PostMapping("/add")
    public String add(@ModelAttribute Category category) {
        categoryService.createCategory(category); // Tworzenie nowej kategorii
        return "redirect:/categories/"; // Przekierowanie na stronę z listą kategorii
    }

    // Strona szczegółów kategorii
    @GetMapping("/details")
    public String details(@RequestParam("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "categories/details"; // Widok szczegółów kategorii
    }

    // Edycja kategorii (GET)
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category); // Przekazanie kategorii do edycji
        return "categories/edit"; // Widok edycji kategorii
    }

    // Edycja kategorii (POST)
    @PostMapping("/edit")
    public String edit(@ModelAttribute Category category) {
        categoryService.updateCategory(category); // Aktualizacja kategorii
        return "redirect:/categories/"; // Przekierowanie na stronę z listą kategorii
    }

    // Usunięcie kategorii
    @GetMapping("/remove")
    public String remove(@RequestParam("id") Long id) {
        categoryService.deleteCategory(id); // Usunięcie kategorii
        return "redirect:/categories/"; // Przekierowanie na stronę z listą kategorii
    }
}
