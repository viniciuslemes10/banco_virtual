package br.com.lemes.VLbank.utils;

import br.com.lemes.VLbank.enums.account.AccountType;
import br.com.lemes.VLbank.exceptions.ArgumentInvalidException;
import br.com.lemes.VLbank.record.account.AccountDTO;
import br.com.lemes.VLbank.record.address.AddressDTO;
import br.com.lemes.VLbank.record.user.UserDTO;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class DTOValidator {

    public static void validateUserDTO(UserDTO data) {
        validateField(data.name(), "Name");
        validateField(data.email(), "Email");
        validateField(data.password(), "Password");
        validateAddressDTO(data.address());
        validateAccountListDTO(data.accounts());
        validateField(data.cpfOrCnpj(), "CPF or CNPJ");
    }

    private static void validateAccountListDTO(List<AccountDTO> accounts) {
        accounts.forEach(account -> {
            validateField(account.accountNumber(), "Account Number");
            validateBalance(account.balance(), "Balance");
            checkIfAccountTypeIsValid(account.accountType());
            validateId(account.bankId(), "Bank ID");
            validateId(account.agencyId(), "Agency ID");
        });
    }

    private static void checkIfAccountTypeIsValid(AccountType accountType) {
        if(accountType == null) {
            throw new ArgumentInvalidException("Account type cannot be null.");
        }

        Set<AccountType> validAccountTypes = EnumSet.allOf(AccountType.class);

        if(!validAccountTypes.contains(accountType)) {
            throw new ArgumentInvalidException("Invalid account type: " + accountType);
        }
    }

    private static void validateBalance(BigDecimal balance, String fieldName) {
        if(balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArgumentInvalidException(fieldName + " cannot be null or negative");
        }
    }

    private static void validateAddressDTO(AddressDTO data) {
        validateField(data.street(), "Street");
        validateField(data.number(), "Number");
        validateField(data.city(), "City");
        validateField(data.state(), "State");
        validateField(data.postalCode(), "Postal Code");
    }

    private static void validateField(String field, String fieldName) {
        if(field == null || field.trim().isEmpty()) {
            throw new ArgumentInvalidException(fieldName + " cannot be null or empty");
        }
    }

    private static void validateId(Long id, String fieldName) {
        if (id == null || id <= 0) {
            throw new ArgumentInvalidException(fieldName + " must be a positive number");
        }
    }
}
