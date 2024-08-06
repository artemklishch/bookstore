package org.example.intro.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.category.CategoryDto;
import org.example.intro.dto.category.CreateCategoryRequestDto;
import org.example.intro.mapper.CategoryMapper;
import org.example.intro.model.Category;
import org.example.intro.repository.category.CategoryRepository;
import org.example.intro.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryMapper.toDto(
                categoryRepository.findById(id).orElse(null)
        );
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto categoryDto) {
        Category categoryFromDb = categoryRepository.findById(id).orElse(null);
        if (categoryFromDb == null) {
            throw new NoSuchElementException("Category with the id " + id + " does not exist");
        }
        categoryFromDb.setName(categoryDto.getName());
        categoryFromDb.setDescription(categoryDto.getDescription());
        categoryRepository.save(categoryFromDb);
        return categoryMapper.toDto(categoryFromDb);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
