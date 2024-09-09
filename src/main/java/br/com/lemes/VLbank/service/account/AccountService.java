package br.com.lemes.VLbank.service.account;

import br.com.lemes.VLbank.exceptions.account.AccountNumberAlreadyExistsException;
import br.com.lemes.VLbank.exceptions.account.InvalidBalanceException;
import br.com.lemes.VLbank.model.account.Account;
import br.com.lemes.VLbank.model.account.CurrentAccount;
import br.com.lemes.VLbank.model.account.CurrentAndSavingsAccount;
import br.com.lemes.VLbank.model.account.SavingsAccount;
import br.com.lemes.VLbank.model.agency.Agency;
import br.com.lemes.VLbank.model.bank.Bank;
import br.com.lemes.VLbank.model.user.User;
import br.com.lemes.VLbank.record.account.AccountDTO;
import br.com.lemes.VLbank.repositories.account.AccountRepository;
import br.com.lemes.VLbank.service.agency.AgencyService;
import br.com.lemes.VLbank.service.bank.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankService bankService;

    @Autowired
    private AgencyService agencyService;

    public boolean doesAccountExist(String accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber);
    }

    public List<Account> createAccountFromDTO(List<AccountDTO> accountListDTO) {
        return accountListDTO.stream()
                .map(dto -> {
                    var bank = bankService.findById(dto.bankId());
                    var agency = agencyService.findById(dto.agencyId());
                    return mapDTOToAccount(dto, bank, agency);
                })
                .toList();
    }

    private Account mapDTOToAccount(AccountDTO data, Bank bank, Agency agency) {
        return switch(data.accountType()) {
                    case CURRENT -> new CurrentAccount(data, bank, agency);
                    case SAVINGS -> new SavingsAccount(data, bank, agency);
                    case CURRENT_AND_SAVINGS -> new CurrentAndSavingsAccount(data, bank, agency);
        };
    }

    public void createAccount(List<Account> accountList, User user) {
        accountList.stream()
                .map(account -> {
                    var isAccountNumber = doesAccountExist(account.getAccountNumber());
                    checkAndThrowIfAccountNumberExists(isAccountNumber);
                    ensureBalanceIsPositive(account.getBalance());
                    account.setUser(user);
                    return accountRepository.save(account);
                });
    }

    private void ensureBalanceIsPositive(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidBalanceException("The balance must be greater than zero.");
        }
    }


    private void checkAndThrowIfAccountNumberExists(boolean isAccountNumber) {
        if(isAccountNumber) {
            throw new AccountNumberAlreadyExistsException("Account number invalid!");
        }
    }
}
