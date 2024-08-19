package org.example.intro.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CreateBookDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotNull
    @Min(0)
    private BigDecimal price;
    @Size(min = 2, max = 255)
    private String description;
    @NotBlank
    private String isbn;
    @Size(min = 10)
    private String coverImage;
    @NotEmpty(message = "Book has to have at least one category")
    private List<Long> categories;
}
