package com.epages.restdocs.openapi.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.data.rest.webmvc.RestMediaTypes.JSON_PATCH_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@FieldDefaults(level = PRIVATE)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ProductRestIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    public void should_get_products() {
        givenProduct();
        givenProduct("Fancy Shirt", "15.10");
        givenProduct("Fancy Shoes", "75.95");

        whenProductsAreRetrieved();

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.products", hasSize(2)))
        ;
    }

    @Test
    @SneakyThrows
    public void should_get_product() {
        givenProduct();

        whenProductIsRetrieved();

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", notNullValue()))
                .andExpect(jsonPath("price", notNullValue()))
        ;
    }

    @Test
    @SneakyThrows
    public void should_create_product() {
        givenProductPayload();

        whenProductIsCreated();

        resultActions
                .andExpect(status().isCreated())
        ;
    }

    @Test
    @SneakyThrows
    public void should_update_product() {
        givenProduct();
        givenProductPayload("Updated name", "12.12");

        whenProductIsPatched();

        resultActions
                .andExpect(status().isOk())
        ;
    }

    @Test
    @SneakyThrows
    public void should_fail_to_update_product_with_negative_price() {
        givenProduct();
        givenProductPayload("Updated name", "-12.12");

        whenProductIsPatched();

        resultActions
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @SneakyThrows
    public void should_partially_update_product() {
        givenProduct();
        givenPatchPayload();

        whenProductIsPatchedJsonPatch();

        resultActions
                .andExpect(status().isOk())
        ;
    }

    @SneakyThrows
    private void givenPatchPayload() {
        json = objectMapper.writeValueAsString(
                ImmutableList.of(
                        ImmutableMap.of(
                                "op", "replace",
                                "path", "/name",
                                "value", "Fancy socks"
                        )
                )
        );
    }

    @SneakyThrows
    private void whenProductIsRetrieved() {
        resultActions = mockMvc.perform(get("/products/{id}", productId));
    }

    @SneakyThrows
    private void whenProductIsPatched() {
        resultActions = mockMvc.perform(patch("/products/{id}", productId)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json));
    }

    @SneakyThrows
    private void whenProductIsPatchedJsonPatch() {
        resultActions = mockMvc.perform(patch("/products/{id}", productId)
                .contentType(JSON_PATCH_JSON)
                .accept(APPLICATION_JSON)
                .content(json));
    }

    @SneakyThrows
    private void whenProductsAreRetrieved() {
        resultActions = mockMvc.perform(get("/products")
                .param("page", "0")
                .param("size", "2")
                .param("sort", "name asc"))
                .andDo(print());
    }
}
