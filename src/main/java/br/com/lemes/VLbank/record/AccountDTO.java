package br.com.lemes.VLbank.record;

import br.com.lemes.VLbank.enums.account.AccountType;

import java.math.BigDecimal;

public record AccountDTO(
        Long id,
        String accountNumber,
        BigDecimal balance,
        AccountType accountType,
        Long bankId,
        Long agencyId,
        Long userId
) {
    public AccountType getAccountType() {
        return this.accountType;
    }
}
