package org.example.intro.dto.book;

public record BookSearchParametersDto(String[] titles, String[] authors, String[] isbns) {
}