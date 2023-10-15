package com.example.TestTaskBank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@RequiredArgsConstructor()
public class AccountTO {

    @Column(name = "name")
    @Size(max = 128)
    @NonNull()
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal balance;

    @NonNull()
    @Size(min =4, max = 4, message = "Pin code must be 4 digits  only")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pin;

    public AccountTO(@NonNull String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }
}
