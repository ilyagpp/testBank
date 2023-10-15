package com.example.TestTaskBank.Controller;

import com.example.TestTaskBank.model.Account;
import com.example.TestTaskBank.model.AccountTO;
import com.example.TestTaskBank.service.AccountService;
import com.example.TestTaskBank.util.AppValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = AccountController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    public static final String REST_URL = "api/account";

    @Autowired
    AccountService accountService;

    @Operation(summary = "GET ACCOUNT DATA BY ID",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "if account not found, decryption in the message field")
            })
    @GetMapping("/{id}")
    public Account findById(@PathVariable Long id){
        return accountService.findById(id);
    }

    @Operation(summary = "CREATE NEW ACCOUNT",
            responses = {
                    @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "if pin of name is incorrect, decryption in the message field")
            })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account>create(@RequestBody AccountTO account){

        String name = account.getName();
        String pin = account.getPin();
        AppValidationUtil.checkNullInput(name, "name");
        AppValidationUtil.syntacticValidatePin(pin);
        Account sAccount = accountService.autoCreate(name, pin);
        return ResponseEntity.status(HttpStatus.CREATED).body(sAccount);
    }

    @Operation(summary = "LIST ALL ACCOUNTS",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
            })
    @GetMapping
    public ResponseEntity<List <AccountTO>>getAll(){
        final List<AccountTO> allAccountsNameBalance = accountService.getAllAccountsNameBalance();
        return ResponseEntity.ok(allAccountsNameBalance);
    }


}
