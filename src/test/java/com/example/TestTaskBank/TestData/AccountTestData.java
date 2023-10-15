package com.example.TestTaskBank.TestData;

import com.example.TestTaskBank.model.Account;

import java.math.BigDecimal;

public class AccountTestData {

    public static final String URLSlash = "/";
    public static final Account account1 =
            new Account(1L,"40800000055000012345",
                    new BigDecimal(300), "Alex", "1122");

}