package org.example.intro.controller;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import java.util.NoSuchElementException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.intro.dto.book.BookDtoWithoutCategoryIds;
import org.example.intro.dto.category.CategoryDto;
import org.example.intro.dto.category.CreateCategoryRequestDto;
import org.example.intro.mapper.CategoryMapper;
import org.example.intro.model.Category;
import org.example.intro.util.HandleDefaultDBData;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryControllerTest extends HandleDefaultDBData {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    private static final String ENDPOINT = "/categories";

    @BeforeAll
    static void setUp(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    @DisplayName("Get all categories")
    @Order(1)
    void getAllCategories_ReturnsCategoriesDtos() throws Exception {
        MvcResult result = mockMvc.perform(get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CategoryDto> actual = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    @DisplayName("Get category by ID")
    @Order(2)
    void getCategoryById_WithExistingId_ReturnsCategoryDto() throws Exception {
        Long id = 1L;

        MvcResult result = mockMvc.perform(get(ENDPOINT + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryDto.class);

        assertNotNull(actual);
        assertEquals(id, actual.getId());
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    @DisplayName("Throws exception when find by wrong ID")
    @Order(3)
    void getCategoryById_WithNoneExistingId_ThrowsException() {
        Long wrongId = 100L;

        Exception exception = assertThrows(
                Exception.class,
                () -> mockMvc.perform(get(ENDPOINT + "/" + wrongId))
                        .andExpect(status().isNotFound())
                        .andReturn()
        );

        Throwable rootCause = exception.getCause();
        assertInstanceOf(NoSuchElementException.class, rootCause);
        assertEquals("Category with id " + wrongId + " not found", rootCause.getMessage());
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    @DisplayName("Get books by category ID")
    @Order(4)
    void getBooksByCategoryId_ReturnsBooksDtoWithoutCategoryId() throws Exception {
        Long id = 1L;

        MvcResult result = mockMvc.perform(get(ENDPOINT + "/" + id + "/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDtoWithoutCategoryIds> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), List.class
        );
        assertNotNull(actual);
        assertEquals(3, actual.size());
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("Create category")
    @Order(5)
    void createCategory_ReturnsCategoryDto() throws Exception {
        CreateCategoryRequestDto expected = new CreateCategoryRequestDto()
                .setName("Test Category")
                .setDescription("Test Description");
        String jsonRequest = objectMapper.writeValueAsString(expected);

        MvcResult result = mockMvc.perform(post(ENDPOINT)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryDto.class);
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("Update category with wrong ID")
    @Order(6)
    void updateCategoryById_WithNonExistingId_ThrowsException() {
        Long wrongId = 100L;

        Exception exception = assertThrows(
                Exception.class,
                () -> mockMvc.perform(put(ENDPOINT + "/" + wrongId)
                                .content(objectMapper.writeValueAsString(new CreateCategoryRequestDto()))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andReturn()
        );

        Throwable rootCause = exception.getCause();
        assertInstanceOf(NoSuchElementException.class, rootCause);
        assertEquals("Category with the id " + wrongId + " does not exist", rootCause.getMessage());
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("Update category")
    @Order(7)
    void updateCategory_WithExistingCategory_UpdatesCategoryDto() throws Exception {
        Long id = 1L;
        CreateCategoryRequestDto categoryDto = new CreateCategoryRequestDto()
                .setName("Nice fantasy")
                .setDescription("Best fantasy books");

        MvcResult categoryResultFromDb = mockMvc.perform(get(ENDPOINT + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Category categoryFromDb = objectMapper.readValue(
                categoryResultFromDb.getResponse().getContentAsString(), Category.class
        );
        categoryMapper.updateCategoryFromDto(categoryDto, categoryFromDb);

        MvcResult savedResult = mockMvc.perform(put(ENDPOINT + "/" + id)
                        .content(objectMapper.writeValueAsString(categoryFromDb))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(savedResult);
        EqualsBuilder.reflectionEquals(
                categoryDto, savedResult.getResponse().getContentAsString(), "id"
        );
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("Delete category by ID")
    @Order(8)
    void deleteCategoryById() throws Exception {
        Long id = 1L;

        MvcResult result = mockMvc.perform(delete(ENDPOINT + "/" + id))
                .andExpect(status().isNoContent())
                .andReturn();

        Assertions.assertNotNull(result);
        assertEquals(204, result.getResponse().getStatus());

        Exception exception = assertThrows(
                Exception.class,
                () -> mockMvc.perform(get(ENDPOINT + "/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andReturn()
        );

        Throwable rootCause = exception.getCause();
        assertInstanceOf(NoSuchElementException.class, rootCause);
        assertEquals("Category with id " + id + " not found", rootCause.getMessage());
    }
}