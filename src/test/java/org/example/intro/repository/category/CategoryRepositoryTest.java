package org.example.intro.repository.category;

import org.example.intro.model.Category;
import org.example.intro.util.HandleDefaultDBData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest extends HandleDefaultDBData {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Find all categories")
    void findAll_ReturnsCategories() {
        Page<Category> categoriesPage = categoryRepository.findAll(PageRequest.of(0,10));
        assertEquals(2, categoriesPage.getTotalElements());
    }
}