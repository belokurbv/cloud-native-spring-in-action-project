package com.polarbookshop.catalogservice.catalogservice;

import com.polarbookshop.catalogservice.config.PolarProperties;
import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestPropertySource("classpath:/greeting.yaml")
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PolarProperties polarProperties;

    @Test
    void whenPostRequestThenBookCreated() {
        var expectedBook = Book.of("1231231231", "Title", "Author", 9.90, "Demo");

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn())
                            .isEqualTo(expectedBook.isbn());
                });
    }

    @Test
    void whenGetRequestGreetingsShown() {
        webTestClient
                .get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(greeting -> {
                    assertThat(greeting).isNotNull();
                    assertThat(greeting).isEqualTo(polarProperties.getGreeting());
                });
    }
}
