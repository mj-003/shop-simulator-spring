package com.example.lista02.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // Pobranie wszystkich kategorii
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Tworzenie nowej kategorii
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    // Usunięcie kategorii
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    // Seed danych do bazy
    public void seed() {
        // Przykładowe kategorie
        Category category1 = new Category("Pieczywo", "K1");
        Category category2 = new Category("Nabiał", "K2");
        Category category3 = new Category("Mięso", "K3");

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
    }

    // Pobranie kategorii po id
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // Aktualizacja kategorii
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }
}
