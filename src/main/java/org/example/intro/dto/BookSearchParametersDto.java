package org.example.intro.dto;

public record BookSearchParametersDto(String[] titles, String[] authors, String[] isbns) {
}