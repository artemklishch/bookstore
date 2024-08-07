package org.example.intro.mapper;

import org.example.intro.config.MapperConfig;
import org.example.intro.dto.category.CategoryDto;
import org.example.intro.dto.category.CreateCategoryRequestDto;
import org.example.intro.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryRequestDto requestDto);

    void updateCategoryFromDto(
            CreateCategoryRequestDto categoryDto,
            @MappingTarget Category category
    );

}
