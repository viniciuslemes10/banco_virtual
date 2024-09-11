package br.com.lemes.VLbank.record.transaction;

import br.com.lemes.VLbank.enums.transaction.TransactionAccountType;
import br.com.lemes.VLbank.enums.transaction.TransactionType;
import br.com.lemes.VLbank.enums.transactions.ExpenseType;
import br.com.lemes.VLbank.record.account.AccountDTO;

import java.math.BigDecimal;

public record TransactionDTO(
        BigDecimal amount,
        AccountDTO account,
        ExpenseType expenseType,
        String description,
        TransactionType transactionType,
        TransactionAccountType transactionAccountType
) {
}
