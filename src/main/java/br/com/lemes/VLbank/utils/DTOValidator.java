package br.com.lemes.VLbank.utils;

import br.com.lemes.VLbank.enums.account.AccountType;
import br.com.lemes.VLbank.enums.transactions.ExpenseType;
import br.com.lemes.VLbank.exceptions.ArgumentInvalidException;
import br.com.lemes.VLbank.record.account.AccountDTO;
import br.com.lemes.VLbank.record.address.AddressDTO;
import br.com.lemes.VLbank.record.transaction.TransactionDTO;
import br.com.lemes.VLbank.record.user.UserDTO;

import java.math.BigDecimal;
import java.util.List;

public class DTOValidator {
    public static final String NAME_FIELD = "Name";
    public static final String EMAIL_FIELD = "Email";
    public static final String PASSWORD_FIELD = "Password";
    public static final String TRANSACTION_FIELD = "Transaction value";
    public static final String ACCOUNT_NUMBER = "Account Number";
    public static final String CPF_OR_CNPJ_FIELD = "CPF or CNPJ";
    public static final String BANK_ID = "Bank ID";
    public static final String AGENCY_ID = "Agency ID";
    public static final String STREET_FIELD = "Street";
    public static final String NUMBER_FIELD = "Number";
    public static final String STATE_FIELD = "Number";
    public static final String CITY_FIELD = "City";
    public static final String POSTAL_CODE_FIELD = "Postal Code";

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

        accounts.forEach(DTOValidator::validateAccountDTO);
    }

    private static void validateTransactionValue(BigDecimal balance, String fieldName) {
        if(balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArgumentInvalidException(fieldName + " cannot be null or negative");
        }
    }

    private static void validateAddressDTO(AddressDTO data) {
        validateField(data.street(), STREET_FIELD);
        validateField(data.number(), NUMBER_FIELD);
        validateField(data.city(), CITY_FIELD);
        validateField(data.state(), STATE_FIELD);
        validateField(data.postalCode(), POSTAL_CODE_FIELD);
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

    public static void validateTransactionDTO(TransactionDTO data) {
        validateAccountDTO(data.account());
        EnumValidator.checkIfEnumIsValid(data.expenseType(), ExpenseType.class, "Expense Type");
    }

    private static void validateAccountDTO(AccountDTO data) {
        validateField(data.accountNumber(), ACCOUNT_NUMBER);
        validateTransactionValue(data.balance(), TRANSACTION_FIELD);
        EnumValidator.checkIfEnumIsValid(data.accountType(), AccountType.class, "Account Type");
        validateId(data.bankId(), BANK_ID);
        validateId(data.agencyId(), AGENCY_ID);
    }
}
