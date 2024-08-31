package org.example.intro.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Accessors(chain = true)
public class CreateCategoryRequestDto {
    @NotBlank
    @Length(max = 255)
    private String name;
    @Length(max = 255)
    private String description;
}
