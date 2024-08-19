package org.example.intro.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateCategoryRequestDto {
    @NotBlank
    @Length(max = 255)
    private String name;
    @Length(max = 255)
    private String description;
}
