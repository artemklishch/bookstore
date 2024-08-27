package org.example.intro.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.intro.dto.book.BookDto;
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

    @WithMockUser(username = "admin", roles={"USER","ADMIN"})
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
}