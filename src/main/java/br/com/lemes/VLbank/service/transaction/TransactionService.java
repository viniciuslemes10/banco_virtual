package br.com.lemes.VLbank.service.transaction;

import br.com.lemes.VLbank.enums.account.AccountType;
import br.com.lemes.VLbank.enums.transaction.TransactionAccountType;
import br.com.lemes.VLbank.enums.transaction.TransactionType;
import br.com.lemes.VLbank.exceptions.ArgumentInvalidException;
import br.com.lemes.VLbank.exceptions.transaction.InsufficientFundsException;
import br.com.lemes.VLbank.model.account.Account;
import br.com.lemes.VLbank.model.account.CurrentAccount;
import br.com.lemes.VLbank.model.account.CurrentAndSavingsAccount;
import br.com.lemes.VLbank.model.account.SavingsAccount;
import br.com.lemes.VLbank.model.transaction.Transaction;
import br.com.lemes.VLbank.record.account.AccountDTO;
import br.com.lemes.VLbank.record.transaction.TransactionDTO;
import br.com.lemes.VLbank.record.transaction.TransactionDetailsDTO;
import br.com.lemes.VLbank.repositories.transaction.TransactionRepository;
import br.com.lemes.VLbank.service.account.AccountService;
import br.com.lemes.VLbank.utils.DTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    public TransactionDetailsDTO create(TransactionDTO data) {
        var account = checkIfThereIsAnAccountNumber(data.account().accountNumber());
        checkAccountType(account, data.account());
        DTOValidator.validateTransactionDTO(data);
        var accountNewBalance = checkSufficientBalanceForTransaction(account, data);

        var transaction = new Transaction(data, accountNewBalance);
        transactionRepository.save(transaction);
        return new TransactionDetailsDTO(accountNewBalance, data);
    }

    private Account checkSufficientBalanceForTransaction(Account account, TransactionDTO data) {
        return switch (account.getAccountType()) {
            case CURRENT -> checkSufficientBalanceForCurrentAccount((CurrentAccount) account, data);
            case SAVINGS -> checkSufficientBalanceForSavingsAccount((SavingsAccount) account, data);
            case CURRENT_AND_SAVINGS -> checkSufficientBalanceForCurrentAndSavingsAccount((CurrentAndSavingsAccount) account, data);
        };
    }

    private CurrentAndSavingsAccount checkSufficientBalanceForCurrentAndSavingsAccount(
            CurrentAndSavingsAccount account, TransactionDTO data) {
        if(data.transactionAccountType() == null) {
            throw new ArgumentInvalidException("Invalid transaction account type for current and savings account.");
        }

        var accountType = data.transactionAccountType();

        return makeTransfer(accountType, account, data);
    }

    private CurrentAndSavingsAccount makeTransfer(TransactionAccountType accountType, CurrentAndSavingsAccount account, TransactionDTO data) {
        if(accountType == TransactionAccountType.CURRENT) {
            return makeACurrentAccountTransfer(data, account);
        }

        if(accountType == TransactionAccountType.SAVINGS) {
            return makeASavingsAccountTransfer(data, account);
        }

        throw new ArgumentInvalidException("Invalid transaction account type for current and savings account.");
    }

    private CurrentAndSavingsAccount makeACurrentAccountTransfer(TransactionDTO data, CurrentAndSavingsAccount account) {
        if(data.transactionType() != null && data.transactionType() == TransactionType.WITHDRAW) {
            var accountNewValue = calculateACurrentAccountNewBalanceForWithdrawal(account, data);

            return (CurrentAndSavingsAccount) makeWithdrawal(accountNewValue);
        }

        if(data.transactionType() != null && data.transactionType() == TransactionType.DEPOSIT) {
           var accountNewBalance = calculateACurrentAccountNewBalanceForDeposit(account, data);

            return (CurrentAndSavingsAccount) makeDeposit(accountNewBalance);
        }

        throw new ArgumentInvalidException("Invalid transaction account type for current and savings account.");
    }

    private Account calculateACurrentAccountNewBalanceForDeposit(CurrentAndSavingsAccount account, TransactionDTO data) {
        BigDecimal currentBalance = account.getCurrentBalance();

        BigDecimal newBalance = currentBalance.add(data.amount());

        validateBalance(newBalance, data);

        account.setCurrentBalance(newBalance);

        return account;
    }

    private Account calculateACurrentAccountNewBalanceForWithdrawal(CurrentAndSavingsAccount account, TransactionDTO data) {
        BigDecimal currentBalance = account.getCurrentBalance();

        BigDecimal totalAvailableBalance = currentBalance.add(account.getAccountLimit());

        validateBalance(totalAvailableBalance, data);

        BigDecimal newBalance = totalAvailableBalance.subtract(data.amount());

        account.setCurrentBalance(newBalance);

        return account;
    }

    private CurrentAndSavingsAccount makeASavingsAccountTransfer(TransactionDTO data, CurrentAndSavingsAccount account) {
        if(data.transactionType() != null && data.transactionType() == TransactionType.WITHDRAW) {

            var accountNewBalance = calculateNewBalanceForWithdrawal(account, data);

            return (CurrentAndSavingsAccount) makeWithdrawal(accountNewBalance);
        }

        if(data.transactionType() != null && data.transactionType() == TransactionType.DEPOSIT) {
            var accountNewBalance = calculateNewBalanceForDeposit(account, data);

            return (CurrentAndSavingsAccount) makeDeposit(accountNewBalance);
        }

        throw new ArgumentInvalidException("Invalid transaction account type for current and savings account.");
    }

    private CurrentAndSavingsAccount calculateNewBalanceForWithdrawal(CurrentAndSavingsAccount account, TransactionDTO data) {
        BigDecimal currentBalance = account.getSavingsBalance();

        BigDecimal totalAvailableBalance = currentBalance.add(account.getAccountLimit());

        validateBalance(totalAvailableBalance, data);

        BigDecimal newBalance = totalAvailableBalance.subtract(data.amount());

        account.setSavingsBalance(newBalance);

        return account;
    }

    private CurrentAndSavingsAccount calculateNewBalanceForDeposit(CurrentAndSavingsAccount account, TransactionDTO data) {
        BigDecimal currentBalance = account.getSavingsBalance();

        BigDecimal newBalance = currentBalance.add(data.amount());

        validateBalance(newBalance, data);

        account.setSavingsBalance(newBalance);

        return account;
    }

    private Account makeDeposit(Account account) {
        try {
            account.setUpdatedDate(LocalDateTime.now());
            return accountService.save(account);
        } catch (Exception e) {
            System.err.println("Erro ao salvar a conta: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private Account makeWithdrawal(Account account) {
        account.setUpdatedDate(LocalDateTime.now());
        return accountService.save(account);
    }

    private void validateBalance(BigDecimal balance, TransactionDTO data) {
        if(data.amount().compareTo(balance) > 0) {
            throw new InsufficientFundsException("Transaction amount exceeds the available balance and credit limit.");
        }
    }

    private SavingsAccount checkSufficientBalanceForSavingsAccount(SavingsAccount savingsAccount, TransactionDTO data) {
        if(data.amount().compareTo(savingsAccount.getBalance()) > 0) {
            throw new InsufficientFundsException("Transaction amount exceeds the available balance and credit limit.");
        }

        return (SavingsAccount) makeTransferToSavingsOrCurrentAccount(savingsAccount, data);
    }

    private CurrentAccount checkSufficientBalanceForCurrentAccount(CurrentAccount currentAccount, TransactionDTO data) {
        if(data.transactionType() == TransactionType.WITHDRAW) {
            var totalAmount = currentAccount.getBalance().add(currentAccount.getAccountLimit());
            if(data.amount().compareTo(totalAmount) > 0) {
                throw new InsufficientFundsException("Transaction amount exceeds the available balance and credit limit.");
            }
        }
        return (CurrentAccount) makeTransferToSavingsOrCurrentAccount(currentAccount, data);
    }

    private Account makeTransferToSavingsOrCurrentAccount(Account account, TransactionDTO data) {
        if(data.transactionType() == TransactionType.DEPOSIT) {
            var accountNewBalance = calculateNewBalanceForCurrentDeposit(account, data);
            return makeDeposit(accountNewBalance);
        }

        if(data.transactionType() == TransactionType.WITHDRAW) {
            var accountNewBalance = calculateNewBalanceForWithdrawal(account, data);
            return makeWithdrawal(accountNewBalance);
        }

        throw new ArgumentInvalidException("Invalid transaction account type for savings account.");
    }

    private Account calculateNewBalanceForCurrentDeposit(Account currentAccount, TransactionDTO data) {
        BigDecimal currentBalance = currentAccount.getBalance();

        BigDecimal newBalance = currentBalance.add(data.amount());

        currentAccount.setBalance(newBalance);

        return currentAccount;
    }

    private Account calculateNewBalanceForWithdrawal(Account currentAccount, TransactionDTO data) {
        BigDecimal currentBalance = currentAccount.getBalance();

        BigDecimal newBalance = currentBalance.subtract(data.amount());

        currentAccount.setBalance(newBalance);

        return currentAccount;
    }

    private void checkAccountType(Account account, AccountDTO data) {
        if(account.getAccountType() != data.accountType()) {
            throw new ArgumentInvalidException("Invalid account type!");
        }
    }

    private Account checkIfThereIsAnAccountNumber(String accountNumber) {
        accountService.isAccountNumberExists(accountNumber);
        return accountService.findByAccountNumber(accountNumber);
    }
}