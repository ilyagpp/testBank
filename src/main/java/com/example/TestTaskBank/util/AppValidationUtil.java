package com.example.TestTaskBank.util;

import com.example.TestTaskBank.exception.InvalidRequestDataException;
import com.example.TestTaskBank.model.Account;

public class AppValidationUtil {

    public static void checkNullInput(Object t, String name){
        if (t == null){
            throw new InvalidRequestDataException(String.format("%s is null", name));
        }
    }

    public static void syntacticValidatePin(String pin) {
        if (pin != null && pin.length() == 4){
            try {
                Integer.parseInt(pin);
            }catch (NumberFormatException e) {
                throw new InvalidRequestDataException("Pin is incorrect");
            }
        } else
            throw new InvalidRequestDataException("Pin is incorrect");
    }

    public static void validateAccountPin(String pin, Account account){
        if (!pin.equals(account.getPin())){
            throw new InvalidRequestDataException("Pin is incorrect");
        }
    }

    public static void validateIdNotNull (Long id){
        if (id == null) {
            throw new InvalidRequestDataException("Request does not contain an ID identifier");
        }
    }

    public static void validateAccountNumber(String accountNumber){
        if (accountNumber == null || accountNumber.length() != 20){
            throw new InvalidRequestDataException("Account is invalid");
        }
    }
}
