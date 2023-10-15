package com.example.TestTaskBank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OPERATIONS")
public class Operation {

    public static final String DEPOSIT = "DEPOSIT";
    public static final String TRANSFER = "TRANSFER";
    public static final String WITHDRAW = "WITHDRAW";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "operation_type")
    @NonNull
    @Size(max = 50)
    private String operationType;

    @Column(name = "account_from")
    private String accountFrom;

    @Column(name = "account_to")
    private String accountTo;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime = LocalDateTime.now();

}
