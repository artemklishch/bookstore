package org.example.intro;

import java.math.BigDecimal;
import org.example.intro.model.Book;
import org.example.intro.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IntroApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(IntroApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book harryPotter = new Book(
                        "Harry Potter 1",
                        "Joan Rouling",
                        "some isbn",
                        BigDecimal.valueOf(200),
                        "The tale about a boy magician",
                        ""
                );
                bookService.save(harryPotter);
                System.out.println(bookService.findAll());
            }
        };
    }

}
