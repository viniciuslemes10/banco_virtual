package br.com.lemes.VLbank.service.user;

import br.com.lemes.VLbank.enums.account.AccountType;
import br.com.lemes.VLbank.exceptions.account.AccountNotFoundException;
import br.com.lemes.VLbank.exceptions.account.InvalidArgumentForAccountTypeException;
import br.com.lemes.VLbank.model.user.User;
import br.com.lemes.VLbank.record.account.AccountDTO;
import br.com.lemes.VLbank.record.user.UserDTO;
import br.com.lemes.VLbank.repositories.user.UserRepository;
import br.com.lemes.VLbank.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    private User convertToEntity(UserDTO data) {
        checkIfAccountPresent(data);
        checkIfAccountTypeIsValid(data);
        List<AccountDTO> accountListDTO = data.accounts().stream().toList();
        var accountList = accountService.createAccountFromDTO(accountListDTO);
        var user = new User(data, accountList);
        userRepository.save(user);
        accountService.createAccount(accountList, user);
        return user;
    }

    private void checkIfAccountTypeIsValid(UserDTO data) {
        Set<AccountType> validAccountTypes = EnumSet.allOf(AccountType.class);

        boolean allTypesValid = data.accounts().stream()
                .map(AccountDTO::getAccountType)
                .allMatch(validAccountTypes::contains);

        if (!allTypesValid) {
            throw new InvalidArgumentForAccountTypeException("Um ou mais tipos de conta são inválidos.");
        }
    }

    private void checkIfAccountPresent(UserDTO data) {
        data.accounts()
                .forEach(accountDTO -> {
                    boolean existAccount = accountService.doesAccountExist(accountDTO.accountNumber());
                    if(!existAccount) {
                        throw new AccountNotFoundException("Account not found for this number!");
                    }
                });
    }
}
