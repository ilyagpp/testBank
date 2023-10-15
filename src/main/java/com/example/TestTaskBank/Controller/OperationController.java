package com.example.TestTaskBank.Controller;

import com.example.TestTaskBank.exception.AmountException;
import com.example.TestTaskBank.exception.InvalidRequestDataException;
import com.example.TestTaskBank.model.Account;
import com.example.TestTaskBank.model.Operation;
import com.example.TestTaskBank.model.OperationInfo;
import com.example.TestTaskBank.service.AccountService;
import com.example.TestTaskBank.service.OperationService;
import com.example.TestTaskBank.util.AppValidationUtil;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value =  OperationController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class OperationController {
    public static final String REST_URL = "api/operation";

    @Autowired
    private OperationService operationService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/{id}")
    public Operation getById (@PathVariable Long id){
        return operationService.findById(id);

    }

    @io.swagger.v3.oas.annotations.Operation(summary = "OPERATION DEPOSIT/TRANSFER/WITHDRAW",
            responses = {
                    @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "if pin of name is incorrect, decryption in the message field"),
                    @ApiResponse(responseCode = "404", description = "if account not found, decryption in the message field")
            })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operation> create (@RequestBody OperationInfo operationInfo) {
        String pin = operationInfo.getPin();
        BigDecimal amount = operationInfo.getAmount();
        String operationType = operationInfo.getOperationType();
        String toAccountNumber = operationInfo.getAccountTo();
        String fromAccountNumber = operationInfo.getAccountFrom();
        if (amount == null || BigDecimal.ZERO.compareTo(amount) >= 0){
           throw new InvalidRequestDataException("Request does not contain an amount, or amount must be greater than 0");
        }
        Operation resultOperation;
        switch (operationType.toUpperCase()){
           case Operation.DEPOSIT ->
               resultOperation = deposit(toAccountNumber, amount);

           case Operation.WITHDRAW ->
                resultOperation = withDraw(fromAccountNumber, amount, pin);

           case Operation.TRANSFER ->
               resultOperation = transfer(fromAccountNumber, toAccountNumber, amount, pin);

           default -> throw new InvalidRequestDataException("Unsupported operation type");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(resultOperation);
    }

    private Operation deposit (String toAccountNumber, BigDecimal amount){
        AppValidationUtil.validateAccountNumber(toAccountNumber);
        Account accountTo = accountService.findByNumber(toAccountNumber);
        accountTo.setBalance(accountTo.getBalance().add(amount));
        accountService.save(accountTo);
        return operationService
                .save(new Operation(null, amount.setScale(4, RoundingMode.HALF_DOWN), Operation.DEPOSIT,
                        null, toAccountNumber, LocalDateTime.now()));
    }

    private Operation withDraw(String fromAccountNumber, BigDecimal amount, String pin){
        AppValidationUtil.syntacticValidatePin(pin);
        AppValidationUtil.validateAccountNumber(fromAccountNumber);
        Account accountFrom = accountService.findByNumber(fromAccountNumber);
        AppValidationUtil.validateAccountPin(pin, accountFrom);
        if (accountFrom.getBalance().compareTo(amount) < 0){
            throw new AmountException("There are insufficient funds in your account");
        }
        BigDecimal newBalance = accountFrom.getBalance().subtract(amount);
        accountFrom.setBalance(newBalance);
        accountService.save(accountFrom);
        return operationService
                .save(new Operation(null, amount.setScale(4, RoundingMode.HALF_DOWN), Operation.WITHDRAW,
                        fromAccountNumber, null, LocalDateTime.now()));
    }

    private Operation transfer(String fromAccountNumber, String toAccountNumber,
                              BigDecimal amount, String pin){
        AppValidationUtil.syntacticValidatePin(pin);
        AppValidationUtil.validateAccountNumber(fromAccountNumber);
        AppValidationUtil.validateAccountNumber(toAccountNumber);
        Account accountFrom = accountService.findByNumber(fromAccountNumber);
        AppValidationUtil.validateAccountPin(pin, accountFrom);
        Account accountTo = accountService.findByNumber(toAccountNumber);
        if (accountFrom.getBalance().compareTo(amount) < 0){
            throw new AmountException("There are insufficient funds in your account");
        }
        BigDecimal newBalanceFrom = accountFrom.getBalance().subtract(amount);
        BigDecimal newBalanceTo = accountTo.getBalance().add(amount);
        accountFrom.setBalance(newBalanceFrom);
        accountTo.setBalance(newBalanceTo);
        accountService.save(accountFrom);
        accountService.save(accountTo);
        return operationService.save(new Operation(null, amount.setScale(4, RoundingMode.HALF_DOWN), Operation.TRANSFER,
                fromAccountNumber, toAccountNumber, LocalDateTime.now()));
    }

}
