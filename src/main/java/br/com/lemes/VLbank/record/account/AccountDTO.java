package br.com.lemes.VLbank.record.account;

import br.com.lemes.VLbank.enums.account.AccountType;
import br.com.lemes.VLbank.model.account.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountDTO(
        String accountNumber,
        BigDecimal balance,
        AccountType accountType,
        Long bankId,
        Long agencyId
) {

    public AccountDTO(Account account) {
        this(account.getAccountNumber(),
                account.getBalance(), account.getAccountType(),
                account.getBank().getId(), account.getAgency().getId());
    }

    public AccountType getAccountType() {
        return this.accountType;
    }
}
