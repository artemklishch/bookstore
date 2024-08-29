package org.example.intro.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.intro.dto.book.BookDto;
import org.example.intro.dto.book.CreateBookDto;
import org.example.intro.util.HandleDefaultDBData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

class BookControllerTest extends HandleDefaultDBData {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String ENDPOINT = "/books";

    @BeforeAll
    static void setUp(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    @DisplayName("Get all books")
    void getAllBooks_ReturnsBookDtos() throws Exception {
        MvcResult result = mockMvc.perform(get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), List.class
        );
        assertNotNull(actual);
        assertEquals(5, actual.size());
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("Create book")
    void createBook_ReturnsBookDto() throws Exception {
        CreateBookDto expected = new CreateBookDto()
                .setAuthor("Test Author")
                .setTitle("Test Title")
                .setDescription("Test Description")
                .setPrice(BigDecimal.valueOf(12))
                .setCategories(List.of(2L))
                .setIsbn("test_isbn2");

        String jsonRequest = objectMapper.writeValueAsString(expected);
        MvcResult createBookResult = mockMvc.perform(post(ENDPOINT)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                createBookResult.getResponse().getContentAsString(), BookDto.class
        );
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    @DisplayName("Find book by ID")
    void findBookById_WithExistingId_ReturnsBookDto() throws Exception {
        Long id = 2L;

        MvcResult result = mockMvc.perform(get(ENDPOINT + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);
        assertNotNull(actual);
        assertEquals(id, actual.getId());
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    @DisplayName("Throw exception when find book by wrong ID")
    void findBookById_WithNoneExistingId_ThrowsException() {
        Long wrongId = 100L;

        Exception exception = assertThrows(
                Exception.class,
                () -> mockMvc.perform(get(ENDPOINT + "/{id}", wrongId)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andReturn()
        );

        Throwable rootCause = exception.getCause();
        assertInstanceOf(NoSuchElementException.class, rootCause);
        assertEquals("Book with id " + wrongId + " not found", rootCause.getMessage());
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("Delete book by ID")
    void deleteBookById_WithExistingId_ReturnsBookDto() throws Exception {
        Long id = 1L;

        MvcResult result = mockMvc.perform(delete(ENDPOINT + "/{id}", id))
                .andExpect(status().isNoContent())
                .andReturn();
        assertNotNull(result);
        assertEquals(204, result.getResponse().getStatus());

        Exception exception = assertThrows(
                Exception.class,
                () -> mockMvc.perform(get(ENDPOINT + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andReturn()
        );
        assertEquals("Book with id " + id + " not found", exception.getCause().getMessage());
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("Throws exception when try to update the book with wrong ID")
    void updateBook_WithNoneExistingBook_ThrowsException() {
        Long wrongId = 100L;

        CreateBookDto expected = new CreateBookDto()
                .setAuthor("Test Author")
                .setTitle("Test Title")
                .setDescription("Test Description")
                .setPrice(BigDecimal.valueOf(12))
                .setCategories(List.of(2L))
                .setIsbn("test_isbn3");

        Exception exception = assertThrows(
                Exception.class,
                () -> mockMvc.perform(get(ENDPOINT + "/{id}", wrongId)
                                .content(objectMapper.writeValueAsString(expected))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andReturn()
        );

        assertEquals("Book with id " + wrongId + " not found", exception.getCause().getMessage());
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("Update the book")
    void updateBook_WithExistingBook_ReturnsBookDto() throws Exception {
        Long id = 2L;

        CreateBookDto expected = new CreateBookDto()
                .setAuthor("Test Author")
                .setTitle("Test Title")
                .setDescription("Test Description")
                .setPrice(BigDecimal.valueOf(12))
                .setCategories(List.of(2L))
                .setIsbn("test_isbn1");

        MvcResult result = mockMvc.perform(put(ENDPOINT + "/{id}", id)
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        assertNotNull(result);
        EqualsBuilder.reflectionEquals(expected, result.getResponse().getContentAsString(), "id");
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    @DisplayName("Find books by query params")
    void findBooksByQueryParams_WithQueryParams_ReturnsBookDtosList() throws Exception {
        String expectedAuthor = "Boris Burda";
        MvcResult result = mockMvc.perform(get(ENDPOINT + "/search")
                        .param("authors", "boris")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<BookDto>>() {}
        );
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(expectedAuthor, actual.get(0).getAuthor());
    }
}