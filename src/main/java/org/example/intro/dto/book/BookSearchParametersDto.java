package org.example.intro.dto.book;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class BookSearchParametersDto {
    private String[] titles;
    private String[] authors;
    private String[] isbns;
}