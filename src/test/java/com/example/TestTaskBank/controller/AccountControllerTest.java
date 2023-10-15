package com.example.TestTaskBank.controller;

import com.example.TestTaskBank.Controller.AccountController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.TestTaskBank.TestData.AccountTestData.URLSlash;
import static com.example.TestTaskBank.TestData.AccountTestData.account1;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAll() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URLSlash + AccountController.REST_URL);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void findById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URLSlash + AccountController.REST_URL + URLSlash + account1.getId());
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    {
                        "accountNumber": "40800000055000012345",
                        "balance": 300.00,
                        "name": "Alex"
                    }
                    """))
                .andDo(print());
    }
    @Test
    void findByNoValidId() throws Exception {
        RequestBuilder requestBuilder  = MockMvcRequestBuilders.get(URLSlash + AccountController.REST_URL + URLSlash + "-1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    void createWithValidRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URLSlash+ AccountController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "pin": 1234,
                        "name": "Egor"
                        }
                        """);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                {
                "name": "Egor",
                "balance": 0
                }
                """))
                .andDo(print());
    }

    @Test
    void createWithInValidRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URLSlash+ AccountController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "pin": "eee"
                        }
                        """);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andDo(print());
    }



}