package org.example.intro.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.book.BookDtoWithoutCategoryIds;
import org.example.intro.dto.category.CategoryDto;
import org.example.intro.dto.category.CreateCategoryRequestDto;
import org.example.intro.service.BookService;
import org.example.intro.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Category management", description = "Endpoints for categories management")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Get all categories", description = "Get all categories, including query parameters")
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Get category by ID")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}/books")
    @Operation(
            summary = "Find books by category",
            description = "Get all books by pointed category, including query parameters"
    )
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            @PathVariable Long id,
            Pageable pageable
    ) {
        return bookService.getBooksByCategoryId(id, pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new category", description = "Create new category")
    public CategoryDto createCategory(@RequestBody CreateCategoryRequestDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update category", description = "Update category")
    public CategoryDto updateCategory(
            @PathVariable Long id,
            @RequestBody CreateCategoryRequestDto categoryDto
    ) {
        return categoryService.update(id, categoryDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete category", description = "Delete category")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
