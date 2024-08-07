package org.example.intro.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateBookDto {
    @NotBlank
    String title;
    @NotBlank
    String author;
    @NotNull
    @Min(0)
    BigDecimal price;
    @Size(min = 2, max = 255)
    String description;
    @NotBlank
    String isbn;
    @Size(min = 10)
    String coverImage;
    @NotEmpty(message = "Book has to have at least one category")
    private List<Long> categories;
}
