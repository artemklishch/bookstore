package org.example.intro.service.impl;

import org.example.intro.dto.category.CategoryDto;
import org.example.intro.dto.category.CreateCategoryRequestDto;
import org.example.intro.mapper.CategoryMapper;
import org.example.intro.model.Category;
import org.example.intro.repository.category.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category testCategory;

    private CategoryDto testCategoryDto;

    @BeforeEach
    void beforeEach() {
        Category category = new Category();
        category.setId(1L);
        category.setName("test name");
        category.setDescription("test description");
        this.testCategory = category;

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        this.testCategoryDto = categoryDto;
    }

    @Test
    @DisplayName("Verify fetching categories")
    void fetchingCategories_ReturnsCategoriesDtos() {
        Page<Category> categoryPage = new PageImpl<>(Collections.singletonList(this.testCategory));
        when(categoryRepository.findAll(PageRequest.of(0, 10))).thenReturn(categoryPage);
        when(categoryMapper.toDto(this.testCategory)).thenReturn(this.testCategoryDto);

        List<CategoryDto> categoryDtos = categoryService.findAll(PageRequest.of(0, 10));

        assertEquals(1, categoryDtos.size());
        assertEquals(this.testCategoryDto, categoryDtos.get(0));
    }

    @Test
    @DisplayName("Fetch category by ID")
    void fetchCategoryById_WithExistingId_ReturnsCategoryDto() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.of(this.testCategory));
        when(categoryMapper.toDto(this.testCategory)).thenReturn(this.testCategoryDto);

        CategoryDto categoryDto = categoryService.getById(id);

        assertEquals(this.testCategoryDto, categoryDto);
        assertEquals(categoryDto.getId(), id);
    }

    @Test
    @DisplayName("Fetch bt wrong ID, should throw exception")
    void fetchCategoryById_WithWrongId_ThrowsException() {
        Long wrongId = 10L;
        when(categoryRepository.findById(wrongId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                NoSuchElementException.class,
                () -> categoryService.getById(wrongId)
        );

        assertEquals("Category with id " + wrongId + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Save new category")
    void saveCategory_ReturnsCategoryDto() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto()
                .setName(this.testCategory.getName())
                .setDescription(this.testCategory.getDescription());
        when(categoryMapper.toEntity(requestDto)).thenReturn(this.testCategory);
        when(categoryRepository.save(this.testCategory)).thenReturn(this.testCategory);
        when(categoryMapper.toDto(this.testCategory)).thenReturn(this.testCategoryDto);

        CategoryDto actual = categoryService.save(requestDto);

        assertEquals(this.testCategoryDto, actual);
    }

    @Test
    @DisplayName("Verify update() method")
    void updateCategory_ReturnsCategoryDto() {
        String updatedName = "UpdatedName";
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto()
                .setName(updatedName)
                .setDescription(this.testCategory.getDescription());
        when(categoryRepository.findById(this.testCategory.getId())).thenReturn(Optional.of(this.testCategory));
        this.testCategory.setName(requestDto.getName());
        when(categoryRepository.save(this.testCategory)).thenReturn(this.testCategory);
        CategoryDto expected = new CategoryDto()
                .setId(this.testCategory.getId())
                .setName(this.testCategory.getName())
                .setDescription(this.testCategory.getDescription());
        when(categoryMapper.toDto(this.testCategory)).thenReturn(expected);

        CategoryDto actual = categoryService.update(this.testCategory.getId(), requestDto);

        assertEquals(expected, actual);
        assertEquals(updatedName, actual.getName());
    }

    @Test
    @DisplayName("Verify throw exception when category is not foun dy ID")
    void updateCategory_WithWrongCategoryId_ThrowsException() {
        Long wrongId = 100L;
        when(categoryRepository.findById(wrongId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                NoSuchElementException.class,
                () -> categoryService.update(wrongId, new CreateCategoryRequestDto())
        );

        assertEquals(
                "Category with the id " + wrongId + " does not exist",
                exception.getMessage()
        );
    }

}