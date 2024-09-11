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
    public static final String NAME_FIELD = "Name";
    public static final String EMAIL_FIELD = "Email";
    public static final String PASSWORD_FIELD = "Password";
    public static final String BALANCE_FIELD = "Balance";
    public static final String ACCOUNT_NUMBER = "Account Number";
    public static final String CPF_OR_CNPJ_FIELD = "CPF or CNPJ";
    public static final String BANK_ID = "Bank ID";
    public static final String AGENCY_ID = "Agency ID";

    public static void validateUserDTO(UserDTO data) {
        validateField(data.name(), NAME_FIELD);
        validateField(data.email(), EMAIL_FIELD);
        validateField(data.password(), PASSWORD_FIELD);
        validateAddressDTO(data.address());
        validateAccountListDTO(data.accounts());
        validateField(data.cpfOrCnpj(), CPF_OR_CNPJ_FIELD);
    }

    private static void validateAccountListDTO(List<AccountDTO> accounts) {
        if (accounts == null || accounts.isEmpty()) {
            throw new ArgumentInvalidException("Accounts list cannot be null or empty.");
        }

        accounts.forEach(account -> {
            validateField(account.accountNumber(), ACCOUNT_NUMBER);
            validateBalance(account.balance(), BALANCE_FIELD);
            checkIfAccountTypeIsValid(account.accountType());
            validateId(account.bankId(), BANK_ID);
            validateId(account.agencyId(), AGENCY_ID);
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
