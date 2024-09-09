package br.com.lemes.VLbank.service.account;

import br.com.lemes.VLbank.exceptions.account.AccountNotFoundException;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Account> createAccountFromDTO(List<AccountDTO> accountListDTO, User user) {
        List<Account> accountList = new ArrayList<>();
        Map<Long, Bank> bankCache = new HashMap<>();
        Map<Long, Agency> agencyCache = new HashMap<>();

        for (AccountDTO dto : accountListDTO) {
            Bank bank = bankCache.computeIfAbsent(dto.bankId(), bankService::findById);

            Agency agency = agencyCache.computeIfAbsent(dto.agencyId(), agencyService::findById);

            Account account = mapDTOToAccount(dto, bank, agency, user);

            ensureBalanceIsPositive(dto.balance());
            accountList.add(account);
        }

        return accountList;
    }

    private Account mapDTOToAccount(AccountDTO data, Bank bank, Agency agency, User user) {
        return switch(data.accountType()) {
                    case CURRENT -> new CurrentAccount(data, bank, agency, user);
                    case SAVINGS -> new SavingsAccount(data, bank, agency, user);
                    case CURRENT_AND_SAVINGS -> new CurrentAndSavingsAccount(data, bank, agency, user);
        };
    }

    private void ensureBalanceIsPositive(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidBalanceException("The balance must be greater than zero.");
        }
    }

    public void saveAll(List<Account> accountList) {
        if (accountList == null || accountList.isEmpty()) {
            throw new AccountNotFoundException("No accounts found");
        }

        accountRepository.saveAll(accountList);
    }
}
