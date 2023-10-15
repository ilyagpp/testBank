package com.example.TestTaskBank.service;

import com.example.TestTaskBank.exception.AppNotFoundException;
import com.example.TestTaskBank.model.Account;
import com.example.TestTaskBank.model.AccountTO;
import com.example.TestTaskBank.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Transactional
    public Account autoCreate(String name, String pin) {
        Account account = new Account();
        String accountNumber = Account.accountNumberGenerator();
        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.ZERO);
        account.setName(name);
        account.setPin(pin);
        Account sAccount = null;

         /* Номер аккаунта создается псевдогенератором,
        возникновение коллизий не исключено*/
        try {
           sAccount = accountRepo.save(account);
        } catch (Exception e) {
            autoCreate(name,pin);
        }

        return sAccount;
    }

    public Account findById(Long id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> {
                    throw new AppNotFoundException("Account not found");
                });
    }

    public Account findByNumber(String number) {
        return accountRepo.findByAccountNumber(number)
                .orElseThrow(() -> {
                    throw new AppNotFoundException(String.format("Account with number %s not found", number));
                });
    }

    @Transactional
    public Account save(Account account) {
        return accountRepo.save(account);
    }

    public List<AccountTO> getAllAccountsNameBalance(){
        List<Account> accounts = accountRepo.findAll();
        final List<AccountTO> accountTOS = accounts
                .stream()
                .map(account -> new AccountTO(account.getName(), account.getBalance()))
                .toList();
        return accountTOS;
    }

}