package br.com.lemes.VLbank.record.transaction;

import br.com.lemes.VLbank.enums.transactions.ExpenseType;
import br.com.lemes.VLbank.model.account.Account;
import br.com.lemes.VLbank.record.account.AccountDTO;

import java.math.BigDecimal;

public record TransactionDetailsDTO(
        BigDecimal amount,
        AccountDTO accountDTO,
        ExpenseType expenseType,
        String description
) {

    public TransactionDetailsDTO(Account accountNewBalance, TransactionDTO data) {
        this(accountNewBalance.getBalance(), new AccountDTO(accountNewBalance),
                data.expenseType(), data.description());
    }
}
