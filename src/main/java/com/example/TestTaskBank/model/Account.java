package com.example.TestTaskBank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Random;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long Id;

    @Column(name = "account_number", unique = true)
    @NonNull
    @Size(min = 20, max = 20, message = "account number must be 20 digits")
    @EqualsAndHashCode.Include
    private String accountNumber;

    @Column(name = "balance")
    @NonNull
    private BigDecimal balance;

    @Column(name = "name")
    @Size(max = 128)
    @NonNull()
    private String name;

    @Column(name = "pin")
    @NonNull()
    @Size(min =4, max = 4, message = "Pin code must be 4 digits  only")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pin;
    public static String accountNumberGenerator() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append("408000000550");
        for (int i = 0; i < 2; i++) {
            sb.append(String.format("%04d", random.nextInt(10000)));
        }
        return sb.toString();
    }

}
