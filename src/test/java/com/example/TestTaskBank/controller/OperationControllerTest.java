package com.example.TestTaskBank.controller;

import com.example.TestTaskBank.Controller.OperationController;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.TestTaskBank.TestData.AccountTestData.URLSlash;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class OperationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Before("getByValidId()")
    void createOperation(){
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URLSlash+ OperationController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "pin": "7777",
                        "operationType": "transfer",
                        "accountTo": 40800000055000012346,
                        "accountFrom": 40800000055000012347,
                        "amount": 5.50
                        }
                        """);
    }

    @Test
    void getByValidId() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URLSlash + OperationController.REST_URL + URLSlash + "1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }


    @Test
    void createWithValidRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URLSlash+ OperationController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "pin": 7777,
                        "operationType": "transfer",
                        "accountTo": 40800000055000012346,
                        "accountFrom": 40800000055000012347,
                        "amount": 2.50
                        }
                        """);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                {
                "operationType": "TRANSFER"
                }
                """))
                .andDo(print());
    }

    @Test
    void createWithInValidRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URLSlash+ OperationController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "pin": "7776",
                        "operationType": "transfer",
                        "accountTo": 40800000055000012346,
                        "accountFrom": 40800000055000012347,
                        "amount": 2.50
                        }
                        """);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


}